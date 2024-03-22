package gbw.melange.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.errors.Errors;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.iocache.DiskShaderCacheUtil;
import gbw.melange.shading.generative.TextureShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.generative.partial.VertexShader;
import gbw.melange.shading.WrappedShader;
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
    private final Queue<IWrappedShader<?>> unCompiled = new ConcurrentLinkedQueue<>(
            //TODO: Find a better way to register the premade ones. Iterating through enum values?
            List.of(TextureShader.TEXTURE, WrappedShader.DEFAULT)
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
        List<IWrappedShader<?>> recentlyCompiled = new ArrayList<>(unCompiled.size());
        while(unCompiled.peek() != null){
            IWrappedShader<?> wrapped = unCompiled.poll();
            wrapped.compile(); //throws
            recentlyCompiled.add(wrapped);
        }

        if (config.getLoggingAspects().contains(IShadingPipelineConfig.LogLevel.COMPILE_STEP_INFO)){
            log.info("Compile step complete without issues for: " + recentlyCompiled.stream().map(IWrappedShader::getLocalName).toList());
        }

        if(cachingEnabled){
            cacheAllStatic(recentlyCompiled);
        }
    }

    private void cacheAllStatic(List<IWrappedShader<?>> recentlyCompiled) {
        if (sendToMain == null) {
            log.warn("Caching is enabled but no way of ensuring execution on main thread (for GL context purposes) is provided. Aborting caching step");
            return;
        }

        List<IWrappedShader<?>> toBeCached = recentlyCompiled.stream()
                //Only statics
                .filter(IWrappedShader::isStatic)
                //Only complex and above
                .filter(shader -> shader.getClassification().abstractValRep > ShaderClassification.PURE_SAMPLER.abstractValRep)
                .toList();

        performCachingStep(toBeCached);
    }

    private void performCachingStep(List<IWrappedShader<?>> toBeCached) {
        int hitsPre = cacheUtil.getHits();
        int cachingFailures = 0;
        final List<String> successFullCaches = new ArrayList<>();

        for (IWrappedShader<?> shader : toBeCached){ //TODO: Check if this can be parallelized
            FileHandle locationOfTexture;
            try {
                locationOfTexture = cacheUtil.cacheOrUpdateExisting(shader);

                //I just love silent errors. It makes it so easy to produce brittle software with bad error handling.
                Errors.checkAndThrow("Caching | " + shader.getLocalName() + " to texture");

                successFullCaches.add(shader.getLocalName());
            } catch (Exception e){ //Catch all of them hands
                log.warn("Caching | Failure for " + shader.getLocalName() + ", skipping.");
                log.warn(e.toString());
                cachingFailures++;
                continue;
            }

            Texture asLoadedFromDisk = new Texture(locationOfTexture);

            log.trace("Caching | Setting cached texture for " + shader.getLocalName());
            ((WrappedShader<?>) shader).setCachedTextureProgram(VertexShader.DEFAULT, FragmentShader.TEXTURE);
            ((WrappedShader<?>) shader).setCachedTexture(asLoadedFromDisk);

        }
        final int actualHits = cacheUtil.getHits() - hitsPre;

        if (config.getLoggingAspects().contains(IShadingPipelineConfig.LogLevel.CACHING_STEP_INFO)) {
            log.info("Cache step complete " + ( cachingFailures > 0 ? "with" : "without" ) + " issue(s) for: " + successFullCaches);
            log.info("Cache hits: " + actualHits + "/" + toBeCached.size() + " failures: " + cachingFailures + "/" + toBeCached.size());
        }
    }


    /** {@inheritDoc} */
    @Override
    public void registerForCompilation(IWrappedShader<?> shader) {
        if(shader.isReady()) return;

        unCompiled.add(shader);
    }

    @Override
    public void useCaching(boolean yesNo) {
        this.cachingEnabled = yesNo;
    }

    @Override
    public void clearCache() {
        if (config.getLoggingAspects().contains(IShadingPipelineConfig.LogLevel.LIFE_CYCLE_INFO)) {
            log.info("Clearing existing generated content");
        }
        cacheUtil.clearCache();
    }

    @Override
    public void dispose() {
        cacheUtil.dispose();
    }
}
