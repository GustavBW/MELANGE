package gbw.melange.common;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.core.discovery.DiscoveryAgent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.NonNull;

public class MelangeApplication<T> extends ApplicationAdapter {
    public static <T> void run(@NonNull Class<T> mainClass) throws Exception{
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Melange");
        new Lwjgl3Application(new MelangeApplication<>(mainClass), config);
    }

    private final Class<T> userMainClass;
    public MelangeApplication(Class<T> userMainClass) throws ClassConfigurationIssue {
        this.userMainClass = userMainClass;
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
