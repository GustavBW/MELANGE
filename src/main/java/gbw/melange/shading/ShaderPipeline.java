package gbw.melange.shading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
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
    private final DiskShaderCacheUtil cacheUtil = new DiskShaderCacheUtil();

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

    private void cacheAllStatic(List<IWrappedShader> recentlyCompiled) {
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

    private void cacheOnMain(List<IWrappedShader> toBeCached) {
        for (IWrappedShader shader : toBeCached){

            FileHandle locationOfTexture;
            try {
                locationOfTexture = cacheUtil.cacheOrUpdateExisting(shader);

                //I just love silent errors. It makes it so easy to produce brittle software with bad error handling.
                int glErrFromCaching = Gdx.gl.glGetError();
                if(glErrFromCaching != GL30.GL_NO_ERROR){
                    log.warn("OpenGL error after caching " + shader.shortName() + " to texture: " + glErrFromCaching + " skipping.");
                    continue;
                }
            } catch (GdxRuntimeException e){
                log.warn("Caching failed for " + shader.shortName() + ", skipping.");
                log.warn(e.toString());
                continue;
            }

            ((WrappedShader) shader).replaceProgram(WrappedShader.TEXTURE.getProgram()); //Is compiled at this point

            final Texture asLoadedFromDisk = new Texture(locationOfTexture);

            //Clear bindings
            ((WrappedShader) shader).changeBindings(new ArrayList<>());
            shader.bindResource((index, program) -> {
                asLoadedFromDisk.bind(index);
                program.setUniformi("u_texture",index);
            }, List.of(asLoadedFromDisk));
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

    @Override
    public void dispose() {

    }
}
