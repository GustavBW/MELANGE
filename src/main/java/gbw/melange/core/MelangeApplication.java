package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.discovery.DiscoveryAgent;
import gbw.melange.core.discovery.ParallelMonitoredExecutionEnvironment;
import gbw.melange.core.interactions.InputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

public class MelangeApplication<T> extends ApplicationAdapter {
    private static final Logger log = LoggerFactory.getLogger(MelangeApplication.class);
    public static <T> void run(@NonNull Class<T> mainClass) throws Exception {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Melange");
        run(mainClass, config);
    }
    public static <T> void run(@NonNull Class<T> mainClass, Lwjgl3ApplicationConfiguration lwjglConfig) throws Exception {
        new Lwjgl3Application(new MelangeApplication<>(mainClass), lwjglConfig);
        Gdx.input.setInputProcessor(new InputListener());
    }
    private DiscoveryAgent<T> discoveryAgent;
    public MelangeApplication(Class<T> userMainClass) throws ClassConfigurationIssue {
        log.info("Running discovery agent");
        discoveryAgent = DiscoveryAgent.locateButDontInstantiate(userMainClass);
    }

    @Override
    public void create(){
        log.info("[MA] Create hit");
        discoveryAgent.instatiateAndPrepare();
        ParallelMonitoredExecutionEnvironment.handleThis(discoveryAgent.getOnInitHookImpls());
    }

    @Override
    public void render(){
        for(OnRender renderHook : discoveryAgent.getOnRenderList()){
            renderHook.onRender();
        }
    }

    @Override
    public void dispose(){

    }

}
