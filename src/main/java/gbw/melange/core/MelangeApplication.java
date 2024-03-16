package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.*;
import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.MelangeConfig;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.core.elementary.ISpaceRegistry;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.shading.impl.ShaderPipeline;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.interactions.IInputListener;
import gbw.melange.core.elementary.SpaceNavigator;
import org.lwjgl.opengl.GL43C;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MelangeApplication<T> extends ApplicationAdapter {
    private static final Logger log = LogManager.getLogger();
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass) throws Exception {
        return run(mainClass, new MelangeConfig());
    }
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass, IMelangeConfig config) throws Exception {
        final Lwjgl3ApplicationConfiguration lwjglConfig = new Lwjgl3ApplicationConfiguration();
        lwjglConfig.setForegroundFPS(60);
        lwjglConfig.setTitle("MelangeApp");
        lwjglConfig.setDecorated(true);
        lwjglConfig.setBackBufferConfig(8, 8, 8, 8, 16, 0, 8); //Samples == AA passes
        // Attempt to make the window transparent
        lwjglConfig.setInitialBackgroundColor(new Color(0, 0, 0, 0)); // Set initial background to transparent
        lwjglConfig.setTransparentFramebuffer(true);
        lwjglConfig.setWindowedMode(1000,1000);
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
    public MelangeApplication(Class<T> userMainClass, IMelangeConfig config) throws ClassConfigurationIssue {
        log.info("Welcome to the spice, MELANGE.");
        this.config = config;
        long discoveryTimeA = System.currentTimeMillis();
        discoveryAgent = DiscoveryAgent.locateButDontInstantiate(userMainClass, config);

        if(config.getLogLevel().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
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

    @Override
    public void create(){
        final long lwjglInitTime = (System.currentTimeMillis() - lwjglInitTimeA);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("LWJGL init time: " + lwjglInitTime + "ms");
        }

        ParallelMonitoredExecutionEnvironment.setInstance(this);

        ISpaceRegistry spaceRegistry;
        InputProcessor inputListener;
        try {
            discoveryAgent.instantiateAndPrepare();
            ApplicationContext context = discoveryAgent.getContext();
            spaceRegistry = context.getBean(ISpaceRegistry.class);
            spaceNavigator = context.getBean(SpaceNavigator.class);
            spaceNavigator.loadFromRegistry(spaceRegistry);

            shaderPipeline = context.getBean(ShaderPipeline.class);
            shaderPipeline.setMainThreadQueue(runOnMainThread);
            if (config.getClearGeneratedOnStart()){
                shaderPipeline.clearCache();
            }

            inputListener = context.getBean(IInputListener.class);
            Gdx.input.setInputProcessor(inputListener);

            final long shaderPipelineTimeA = System.currentTimeMillis();
            shaderPipeline.useCaching(config.getUseCaching());
            shaderPipeline.compileAndCache();

            if(config.getLogLevel().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)){
                log.info("Shader pipeline time: " + (System.currentTimeMillis() - shaderPipelineTimeA) + "ms");
            }

        } catch (ViewConfigurationIssue | ShaderCompilationIssue | IOException e) {
            //Escalation allowed since we're within the boot sequence
            throw new RuntimeException(e);
        }
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());

        final long elementResolvePassTimeA = System.currentTimeMillis();
        spaceNavigator.getOrderedList().forEach(ISpace::resolveConstraints);
        if(config.getLogLevel().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("Space constraints resolution: " + (System.currentTimeMillis() - elementResolvePassTimeA) + "ms");
        }

        Gdx.gl.glEnable(GL30.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha
        if(config.getEnableGLDebug()){
            Gdx.gl.glEnable(GL43C.GL_DEBUG_OUTPUT);
        }

        testCam = new PerspectiveCamera();
        viewport = new FitViewport(1, 1, testCam);
        testCam.update();

        final long totalBootTime = System.currentTimeMillis() - bootTimeA;
        if(config.getLogLevel().contains(IMelangeConfig.LogLevel.BOOT_SEQ_INFO)) {
            log.info("Total startup time: " + (totalBootTime) + "ms");
            log.info("MELANGE Framework startup time: " + (totalBootTime - lwjglInitTime) + "ms");
        }
    }
    private long frame = 0;

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
        frame++;

        //Render spaces
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.render(testCam.view);
        }
        int glErrCausedBySpaces = Gdx.gl.glGetError();
        if(glErrCausedBySpaces != GL30.GL_NO_ERROR){
            log.warn("OpenGL error after rendering spaces: " + glErrCausedBySpaces);
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
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.dispose();
        }
        shaderPipeline.dispose();
        ParallelMonitoredExecutionEnvironment.shutdown();
    }

}
