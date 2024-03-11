package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import gbw.melange.common.assets.Selector;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.ISpaceRegistry;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.errors.ShaderCompilationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.interactions.InputListener;
import gbw.melange.elements.navigation.ISpaceManager;
import gbw.melange.elements.navigation.SpaceManager;
import gbw.melange.shading.ManagedShaderPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Queue;
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
    private static long lwjglTimeB, lwjglInitTimeA;
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
    private final ISpaceManager spaceManager = new SpaceManager();
    @Override
    public void create(){
        lwjglTimeB = System.currentTimeMillis();
        log.info("LWJGL init time: " + (lwjglTimeB - lwjglInitTimeA) + "ms");

        Gdx.input.setInputProcessor(new InputListener());
        ISpaceRegistry spaceRegistry;
        try {
            discoveryAgent.instatiateAndPrepare();
            spaceRegistry = discoveryAgent.getContext().getBean(ISpaceRegistry.class);
            spaceManager.loadFromRegistry(spaceRegistry);
            final long shaderPipelineTimeA = System.currentTimeMillis();
            ManagedShaderPipeline.run();
            log.info("Shader pipeline time: " + (System.currentTimeMillis() - shaderPipelineTimeA) + "ms");
        } catch (ViewConfigurationIssue | ShaderCompilationIssue e) {
            //Escalation allowed since we're within the boot sequence
            throw new RuntimeException(e);
        }
        ParallelMonitoredExecutionEnvironment.setInstance(this);
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());

        final long elementResolvePassTimeA = System.currentTimeMillis();


        Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha

        final long totalBootTime = System.currentTimeMillis() - bootTimeA;
        log.info("Total startup time: " + (totalBootTime) + "ms");
        log.info("MELANGE Framework startup time: " + (totalBootTime - (lwjglTimeB - lwjglInitTimeA)) + "ms");
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
        //Handle backlog
        while(runOnMainThread.peek() != null){
            runOnMainThread.poll().run();
        }

        //Render spaces
        for(ISpace space : spaceManager.getOrderedList()){
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

    }

    public void pause() {

    }

    public void resume() {

    }

    @Override
    public void dispose(){
        for(ISpace space : spaceManager.getOrderedList()){
            space.dispose();
        }
        ParallelMonitoredExecutionEnvironment.shutdown();
    }

}
