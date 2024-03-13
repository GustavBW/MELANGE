package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.core.elementary.ISpaceRegistry;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.shading.IShaderPipeline;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.interactions.IInputListener;
import gbw.melange.common.navigation.ISpaceNavigator;
import gbw.melange.core.elementary.SpaceNavigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MelangeApplication<T> extends ApplicationAdapter {
    private static final Logger log = LoggerFactory.getLogger(MelangeApplication.class);
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass) throws Exception {
        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Melange");
        config.setDecorated(true);
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4); //Samples == AA passes
        // Attempt to make the window transparent
        config.setInitialBackgroundColor(new Color(0, 0, 0, 0)); // Set initial background to transparent
        config.setTransparentFramebuffer(true);
        return run(mainClass, config);
    }
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass, Lwjgl3ApplicationConfiguration lwjglConfig) throws Exception {
        bootTimeA = System.currentTimeMillis();
        MelangeApplication<T> app = new MelangeApplication<>(mainClass);

        lwjglInitTimeA = System.currentTimeMillis();
        new Lwjgl3Application(app, lwjglConfig); //TODO: THIS GUY NEVER RETURNS. Time for threads

        return app.getContext();
    }
    private static long lwjglInitTimeA;
    private DiscoveryAgent<T> discoveryAgent;
    private static long bootTimeA;
    public MelangeApplication(Class<T> userMainClass) throws ClassConfigurationIssue {
        log.info("Welcome to the spice, MELANGE.");

        long discoveryTimeA = System.currentTimeMillis();
        discoveryAgent = DiscoveryAgent.locateButDontInstantiate(userMainClass);
        log.info("Discovery pass time: " + (System.currentTimeMillis() - discoveryTimeA) + "ms");
    }

    private ApplicationContext getContext(){
        return discoveryAgent.getContext();
    }
    private Camera testCam;
    private Viewport viewport;
    private ISpaceNavigator spaceNavigator;

    @Override
    public void create(){
        final long lwjglInitTime = (System.currentTimeMillis() - lwjglInitTimeA);
        log.info("LWJGL init time: " + lwjglInitTime + "ms");

        ParallelMonitoredExecutionEnvironment.setInstance(this);
        ISpaceRegistry spaceRegistry;
        InputProcessor inputListener;
        IShaderPipeline shaderPipeline;
        try {
            discoveryAgent.instantiateAndPrepare();
            spaceRegistry = getContext().getBean(ISpaceRegistry.class);
            spaceNavigator = getContext().getBean(ISpaceNavigator.class);
            shaderPipeline = getContext().getBean(IShaderPipeline.class);
            ((SpaceNavigator) spaceNavigator).loadFromRegistry(spaceRegistry); //TODO: Swap to visibility control when modularized

            inputListener = getContext().getBean(IInputListener.class);
            Gdx.input.setInputProcessor(inputListener);

            final long shaderPipelineTimeA = System.currentTimeMillis();
            shaderPipeline.compileAll();
            log.info("Shader pipeline time: " + (System.currentTimeMillis() - shaderPipelineTimeA) + "ms");

        } catch (ViewConfigurationIssue | ShaderCompilationIssue e) {
            //Escalation allowed since we're within the boot sequence
            throw new RuntimeException(e);
        }
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());

        final long elementResolvePassTimeA = System.currentTimeMillis();
        spaceNavigator.getOrderedList().forEach(ISpace::resolveConstraints);
        log.info("Space constraints resolution: " + (System.currentTimeMillis() - elementResolvePassTimeA) + "ms");

        Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha

        testCam = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, testCam);

        final long totalBootTime = System.currentTimeMillis() - bootTimeA;
        log.info("Total startup time: " + (totalBootTime) + "ms");
        log.info("MELANGE Framework startup time: " + (totalBootTime - lwjglInitTime) + "ms");
    }
    private long frame = 0;
    /**
     * Task backlog from last render pass or within last render pass
     */
    private final ConcurrentLinkedQueue<Runnable> runOnMainThread = new ConcurrentLinkedQueue<>();
    void handleOnMain(Runnable any){
        if(any == null) return;
        runOnMainThread.add(any);
    }

    @Override
    public void render(){
        frame++;
        testCam.update();
        //Handle backlog
        while(runOnMainThread.peek() != null){
            runOnMainThread.poll().run();
        }

        //Render spaces
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.render();
        }
        int glErrCausedBySpaces = Gdx.gl.glGetError();
        if(glErrCausedBySpaces != GL20.GL_NO_ERROR){
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

    public void pause() {

    }

    public void resume() {

    }

    @Override
    public void dispose(){
        for(ISpace space : spaceNavigator.getOrderedList()){
            space.dispose();
        }
        ParallelMonitoredExecutionEnvironment.shutdown();
    }

}
