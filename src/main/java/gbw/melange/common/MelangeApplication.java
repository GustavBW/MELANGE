package gbw.melange.common;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.core.discovery.DiscoveryAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
    }

    public MelangeApplication(Class<T> userMainClass) throws ClassConfigurationIssue {
        log.info("Running discovery agent.");
        DiscoveryAgent<T> discoveryAgent = DiscoveryAgent.run(userMainClass);
    }

    @Override
    public void create(){
        System.out.println("[MA] Create hit");
    }

    @Override
    public void render(){
    }

    @Override
    public void dispose(){

    }

}
