package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.viewport.*;
import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.core.elementary.ISpaceRegistry;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.mesh.errors.InvalidMeshIssue;
import gbw.melange.mesh.errors.MeshProcessingIssue;
import gbw.melange.mesh.services.MeshPipeline;
import gbw.melange.shading.errors.Errors;
import gbw.melange.shading.services.ShaderPipeline;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.interactions.IInputListener;
import gbw.melange.core.elementary.SpaceNavigator;
import gbw.melange.tooling.DevTools;
import gbw.melange.tooling.SuperDicyInternalReferences;
import org.lwjgl.opengl.GL43C;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MelangeApplication<T> extends ApplicationAdapter {
    private static final Logger log = LogManager.getLogger();
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass) throws Exception {
        return run(mainClass, new MelangeConfig());
    }
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass, IMelangeConfig config) throws Exception {
        final Lwjgl3ApplicationConfiguration lwjglConfig = new Lwjgl3ApplicationConfiguration();
        lwjglConfig.setTitle("MelangeApp");
        lwjglConfig.setDecorated(true);
        lwjglConfig.setBackBufferConfig(8, 8, 8, 8, 16, 0, 8); //Samples == AA passes
        // Attempt to make the window transparent
        lwjglConfig.setInitialBackgroundColor(new Color(0, 0, 0, 0)); // Set initial background to transparent
        lwjglConfig.setTransparentFramebuffer(true);
        lwjglConfig.setWindowedMode(1000,1000);
        lwjglConfig.enableGLDebugOutput(config.getEnableGLDebug(), System.out);
        lwjglConfig.setInitialVisible(true);
        lwjglConfig.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL30,3,3);
        return run(mainClass, lwjglConfig, config);
    }
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass, Lwjgl3ApplicationConfiguration lwjglConfig, IMelangeConfig melangeConfig) throws Exception {
        bootTimeA = System.currentTimeMillis();
        MelangeApplication<T> app = new MelangeApplication<>(mainClass, melangeConfig);

        lwjglInitTimeA = System.currentTimeMillis();
        new Lwjgl3Application(app, lwjglConfig); //TODO: THIS GUY NEVER RETURNS. Time for threads

        return app.getContext();
    }
    private static long lwjglInitTimeA;
    private DiscoveryAgent<T> discoveryAgent;
    private static long bootTimeA;
    private final IMelangeConfig config;
    private MelangeApplication(Class<T> userMainClass, IMelangeConfig config) throws ClassConfigurationIssue {
        log.info("Welcome to the spice, MELANGE.");
        this.config = config;
        config.resolve();
        long discoveryTimeA = System.currentTimeMillis();
        discoveryAgent = DiscoveryAgent.locateButDontInstantiate(userMainClass, config);

        if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("Discovery pass time: " + (System.currentTimeMillis() - discoveryTimeA) + "ms");
        }
    }

    private ApplicationContext getContext(){
        return discoveryAgent.getContext();
    }
    private PerspectiveCamera testCam;
    private Viewport viewport;
    private SpaceNavigator spaceNavigator;
    private ShaderPipeline shaderPipeline;
    private MeshPipeline meshPipeline;

    @Override
    public void create(){
        final long lwjglInitTime = (System.currentTimeMillis() - lwjglInitTimeA);

        if (config.getLoggingAspects().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("LWJGL init time: " + lwjglInitTime + "ms");
        }

        Errors.checkAndThrow("create lifecycle just begun");

        ParallelMonitoredExecutionEnvironment.setInstance(this);

        ISpaceRegistry spaceRegistry;
        InputProcessor inputListener;
        DevTools devTools;
        try {
            discoveryAgent.instantiateAndPrepare();
            ApplicationContext context = discoveryAgent.getContext();
            devTools = context.getBean(DevTools.class);
            spaceRegistry = context.getBean(ISpaceRegistry.class);
            spaceNavigator = context.getBean(SpaceNavigator.class);
            spaceNavigator.loadFromRegistry(spaceRegistry);

            shaderPipeline = context.getBean(ShaderPipeline.class);
            shaderPipeline.setMainThreadQueue(runOnMainThread);
            if (config.getClearGeneratedOnStart()){
                shaderPipeline.clearCache();
            }
            shaderPipeline.useCaching(config.getUseCaching());
            shaderPipeline.compileAndCache();

            meshPipeline = context.getBean(MeshPipeline.class);
            meshPipeline.beginProcessing();

            inputListener = context.getBean(IInputListener.class);
            Gdx.input.setInputProcessor(inputListener);

            final long shaderPipelineTimeA = System.currentTimeMillis();

            if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)){
                log.info("Shader pipeline time: " + (System.currentTimeMillis() - shaderPipelineTimeA) + "ms");
            }

        } catch (ViewConfigurationIssue | ShaderCompilationIssue | MeshProcessingIssue | InvalidMeshIssue | IOException e) {
            //Escalation allowed since we're within the boot sequence
            throw new RuntimeException(e);
        }
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());

        final long elementResolvePassTimeA = System.currentTimeMillis();
        spaceNavigator.getOrderedList().forEach(ISpace::resolveConstraints);
        if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("Space constraints resolution: " + (System.currentTimeMillis() - elementResolvePassTimeA) + "ms");
        }

        Gdx.gl.glEnable(GL30.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha
        Errors.checkAndThrow("setting blending function to GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA");

        IntBuffer buffer = BufferUtils.newIntBuffer(1);
        Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_IMAGE_UNITS, buffer);
        log.debug("OpenGL Texture unit range: " + buffer.get(0));

        //Following is from: https://gamefromscratch.com/libgdx-tutorial-part-16-cameras/
        float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

        testCam = new PerspectiveCamera(90, 1 * aspectRatio, 1);
        testCam.position.set( 0,0,0);
        testCam.lookAt(0,0,0);
        viewport = new FitViewport(100, 100, testCam);
        testCam.near = 1f;
        testCam.far = 10000;
        testCam.update();
        devTools.setSdir(new SuperDicyInternalReferences(testCam, viewport));

        final long totalBootTime = System.currentTimeMillis() - bootTimeA;
        if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("Total startup time: " + (totalBootTime) + "ms");
            log.info("MELANGE Framework startup time: " + (totalBootTime - lwjglInitTime) + "ms");
        }
        timestampOfRenderCycleStart = System.currentTimeMillis();
    }
    private long frames = 0;
    private long timestampOfRenderCycleStart;

    /**
     * Task backlog from last render pass or within last render pass.
     * This is used extremely sparingly, as any error occurring here is going to be tremendously annoying to debug.
     */
    private final ConcurrentLinkedQueue<Runnable> runOnMainThread = new ConcurrentLinkedQueue<>();
    void handleOnMain(Runnable any){
        if(any == null) return;
        runOnMainThread.add(any);
    }

    @Override
    public void render(){
        //Handle backlog
        while(runOnMainThread.peek() != null){
            runOnMainThread.poll().run();
        }
        frames++;

        //Render spaces
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.render(testCam.combined);
            Errors.checkAndLog(log, "rendering space " + space + " with Mat4: " + testCam.view);
        }

        //Hooks
        for(OnRender renderHook : discoveryAgent.getOnRenderList()){
            renderHook.onRender(Gdx.graphics.getDeltaTime());
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose(){
        long delta = System.currentTimeMillis() - timestampOfRenderCycleStart;
        log.debug("avg frame time for session: " + (delta / frames) + " ms per frame, or " + (1000 / (delta / frames)) + " fps");
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.dispose();
        }
        shaderPipeline.dispose();
        ParallelMonitoredExecutionEnvironment.shutdown();
    }

}
