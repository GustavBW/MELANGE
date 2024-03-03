package gbw.melange.common;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gbw.melange.core.discovery.DiscoveryAgent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.NonNull;

public class MelangeApplication<T> extends ApplicationAdapter {
    public static <T> void run(@NonNull Class<T> mainClass){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Melange");
        new Lwjgl3Application(new MelangeApplication(mainClass), config);
    }

    private final Class<T> userMainClass;
    public MelangeApplication(Class<T> userMainClass){
        this.userMainClass = userMainClass;
    }

    @Override
    public void create(){
        System.out.println("[MA] Create hit");
        DiscoveryAgent<T> discoveryAgent = DiscoveryAgent.run(userMainClass);
    }

    @Override
    public void render(){
    }

    @Override
    public void dispose(){

    }

}
