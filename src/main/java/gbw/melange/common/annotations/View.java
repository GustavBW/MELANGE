package gbw.melange.common.annotations;

import gbw.melange.welcomeapp.HomeScreen;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component //NB: This annotation is only marked with Component so Spring stops complaining. No ApplicationContext.scan() is ever run, all discovery is partially manual.
public @interface View {
    int HOME_SCREEN = -1;
    int DEFAULT = 0;
    /**
     * Render ordering, a higher value is more in the background, a layer of -1 is the top, initially user-facing view. <br/>
     * <pre>
     *     {@code
     *     @View( layer = 1 )
     *     public class SomeView { ... }
     *     }
     * </pre>
     *
     */
    int layer() default DEFAULT;
}
