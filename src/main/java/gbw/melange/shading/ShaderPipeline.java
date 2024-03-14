package gbw.melange.shading;

import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.iocache.DiskShaderCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ShaderPipeline implements IShaderPipeline {

    private static final Logger log = LoggerFactory.getLogger(ShaderPipeline.class);
    private final Queue<IWrappedShader> unCompiled = new ConcurrentLinkedQueue<>(
            //TODO: Find a better way to register the premade ones. Iterating through enum values?
            List.of(WrappedShader.TEXTURE, WrappedShader.DEFAULT, WrappedShader.NONE)
    );
    private boolean cachingEnabled = false;

    /** {@inheritDoc} */
    @Override
    public void compileAndCache() throws ShaderCompilationIssue, IOException {
        List<IWrappedShader> recentlyCompiled = new ArrayList<>(unCompiled.size());
        while(unCompiled.peek() != null){
            IWrappedShader wrapped = unCompiled.poll();
            wrapped.compile(); //throws
            recentlyCompiled.add(wrapped);
        }
        log.info("Compile step complete without issues for: " + recentlyCompiled.stream().map(IWrappedShader::shortName).toList());

        if(!cachingEnabled){
            return;
        }

        List<IWrappedShader> toBeCached = recentlyCompiled.stream().filter(IWrappedShader::isStatic).toList();

        for (IWrappedShader shader : toBeCached){
            Texture toBind = DiskShaderCacheUtil.cacheOrUpdateExisting(shader);
            ((WrappedShader) shader).replaceProgram(WrappedShader.TEXTURE.getProgram());
            ((WrappedShader) shader).changeBindings(program -> {
                toBind.bind(((WrappedShader) shader).getInstanceId() + 100);
                program.setUniformi("u_texture", ((WrappedShader) shader).getInstanceId() + 100);
            });
        }

        log.info("Cache step complete without issue for: " + toBeCached.stream().map(IWrappedShader::shortName).toList());
    }



    /** {@inheritDoc} */
    @Override
    public void registerForCompilation(IWrappedShader shader) {
        if(shader.isReady()) return;

        unCompiled.add(shader);
    }

    @Override
    public void useCaching(boolean yesNo) {
        this.cachingEnabled = yesNo;
    }
}
