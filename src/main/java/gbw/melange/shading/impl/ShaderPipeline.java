package gbw.melange.shading.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import gbw.melange.shading.*;
import gbw.melange.shading.errors.Errors;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.iocache.DiskShaderCacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ShaderPipeline implements IShaderPipeline {

    private static final Logger log = LogManager.getLogger(ShaderPipeline.class);
    private final Queue<IWrappedShader> unCompiled = new ConcurrentLinkedQueue<>(
            //TODO: Find a better way to register the premade ones. Iterating through enum values?
            List.of(WrappedShader.TEXTURE, WrappedShader.DEFAULT)
    );
    private boolean cachingEnabled = false;
    private Queue<Runnable> sendToMain;
    private final DiskShaderCacheUtil cacheUtil = new DiskShaderCacheUtil();
    private final IShadingPipelineConfig config;

    @Autowired
    public ShaderPipeline(IShadingPipelineConfig config){
        this.config = config;
    }

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

        if (config.getLogLevel().contains(IShadingPipelineConfig.PipelineLogLevel.COMPILE_STEP_INFO)){
            log.info("Compile step complete without issues for: " + recentlyCompiled.stream().map(IWrappedShader::shortName).toList());
        }

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

        performCachingStep(toBeCached);
    }

    private void performCachingStep(List<IWrappedShader> toBeCached) {
        int hitsPre = cacheUtil.getHits();

        for (IWrappedShader shader : toBeCached){ //TODO: Check if this can be parallelized
            FileHandle locationOfTexture;
            try {
                locationOfTexture = cacheUtil.cacheOrUpdateExisting(shader);

                //I just love silent errors. It makes it so easy to produce brittle software with bad error handling.
                int glErrFromCaching = Gdx.gl.glGetError();
                if(glErrFromCaching != GL30.GL_NO_ERROR){
                    log.warn("OpenGL error after caching " + shader.shortName() + " to texture: " + glErrFromCaching + ", skipping.");
                    continue;
                }
            } catch (GdxRuntimeException e){
                log.warn("Caching failed for " + shader.shortName() + ", skipping.");
                log.warn(e.toString());
                continue;
            }

            ((WrappedShader) shader).clearBindings();
            ((WrappedShader) shader).replaceProgram(VertexShader.DEFAULT, FragmentShader.TEXTURE);

            Texture asLoadedFromDisk = new Texture(locationOfTexture);

            shader.bindResource((index, program) -> {
                log.debug("| " + shader.shortName() + " | binding texture " + asLoadedFromDisk + " bound to " + program + " at index: " + index);

                asLoadedFromDisk.bind(index);
                Errors.checkAndThrow("binding texture " + asLoadedFromDisk + " to shader: " + program + " at index: " + index);

                program.setUniformi(GLShaderAttr.TEXTURE.glValue(), index);
                Errors.checkAndThrow("setting shader uniformi for " + program + " at index: " + index + " as param: " + GLShaderAttr.TEXTURE.glValue());

            }, asLoadedFromDisk);
        }
        final int actualHits = cacheUtil.getHits() - hitsPre;

        if (config.getLogLevel().contains(IShadingPipelineConfig.PipelineLogLevel.CACHING_STEP_INFO)) {
            log.info("Cache step complete without issue for: " + toBeCached.stream().map(IWrappedShader::shortName).toList());
            log.info("Cache hits: " + actualHits + "/" + toBeCached.size());
        }
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
    public void clearCache() {
        if (config.getLogLevel().contains(IShadingPipelineConfig.PipelineLogLevel.LIFE_CYCLE_INFO)) {
            log.info("Clearing existing generated content");
        }
        cacheUtil.clearCache();
    }

    @Override
    public void dispose() {
        cacheUtil.dispose();
    }
}
