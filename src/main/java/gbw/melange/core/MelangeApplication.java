package gbw.melange.core;

import com.badlogic.gdx.ApplicationAdapter;
import gbw.melange.common.annotations.Space;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MelangeApplication extends ApplicationAdapter {

    @Override
    public void create(){
        //Invoke spring to find user impl
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan();
        context.getBean(Space.class);

    }

    @Override
    public void render(){
    }

    @Override
    public void dispose(){

    }

}
