package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.ISpaceRegistry;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.discovery.ParallelMonitoredExecutionEnvironment;
import gbw.melange.core.interactions.InputListener;
import gbw.melange.elements.navigation.ISpaceManager;
import gbw.melange.elements.navigation.SpaceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public class MelangeApplication<T> extends ApplicationAdapter {
    private static final Logger log = LoggerFactory.getLogger(MelangeApplication.class);
    public static <T> ApplicationContext run(@NonNull Class<T> mainClass) throws Exception {
        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Melange");
        config.setDecorated(true);
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4); //Samples == AA passes
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

    public ApplicationContext getContext(){
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
        } catch (ViewConfigurationIssue e) {
            //Escalation allowed since we're within the boot sequence
            throw new RuntimeException(e);
        }
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());
        final long totalBootTime = System.currentTimeMillis() - bootTimeA;

        log.info("Total startup time: " + (totalBootTime) + "ms");
        log.info("MELANGE Framework startup time: " + (totalBootTime - (lwjglTimeB - lwjglInitTimeA)) + "ms");

    }

    @Override
    public void render(){
        //Clear to black
        Gdx.gl.glClearColor(1,1,1,1);
        //Render spaces
        for(ISpace space : spaceManager.getOrderedList()){
            space.render();
        }
        //Hooks
        for(OnRender renderHook : discoveryAgent.getOnRenderList()){
            renderHook.onRender();
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
    }

}
