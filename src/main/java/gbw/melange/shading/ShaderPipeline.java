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
    private Queue<Runnable> sendToMain;
    public void setMainThreadQueue(Queue<Runnable> sendToMain){
        this.sendToMain = sendToMain;
    }

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

        if(cachingEnabled){
            cacheAllStatic(recentlyCompiled);
        }
    }

    private void cacheAllStatic(List<IWrappedShader> recentlyCompiled) throws ShaderCompilationIssue, IOException {
        if (sendToMain == null) {
            log.warn("Caching is enabled but no way of ensuring execution on main thread (for GL context purposes) is provided. Aborting caching step");
            return;
        }

        List<IWrappedShader> toBeCached = recentlyCompiled.stream()
                //Only statics
                .filter(IWrappedShader::isStatic)
                //Only complex and above
                .filter(shader -> shader.getClassification().abstractValRep > ShaderClassification.PURE_SAMPLER.abstractValRep)
                .toList();

        sendToMain.add(() -> cacheOnMain(toBeCached));
    }

    private static void cacheOnMain(List<IWrappedShader> toBeCached) {
        for (IWrappedShader shader : toBeCached){

            Texture toBind;
            try {
                toBind = DiskShaderCacheUtil.cacheOrUpdateExisting(shader);
                //TODO: GL err check right here
            } catch (Exception e){
                log.warn("Caching failed for " + shader.shortName() + ", skipping.");
                log.warn(e.toString());
                continue;
            }

            ((WrappedShader) shader).replaceProgram(WrappedShader.TEXTURE.getProgram()); //Is compiled at this point

            List<ShaderResourceBinding> newBindings = new ArrayList<>();
            newBindings.add(
                    (index, program) -> {
                        toBind.bind(index);
                        program.setUniformi("u_texture", index);
                    }
            );

            ((WrappedShader) shader).changeBindings(newBindings);
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
