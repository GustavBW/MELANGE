package gbw.melange.common.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>View class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component //NB: This annotation is only marked with Component so Spring stops complaining. Discovery of Views is manual using Reflections
public @interface View {
    enum FocusPolicy {
        /**
         * Allow the latest view behind this.
         */
        RETAIN_LATEST,
        /**
         * Allow all views behind this, in same order.
         */
        RETAIN_ALL,
        /**
         * Allow no views but this one when focused
         */
        OPAQUE;
    }

    int HOME_SCREEN_LAYER = -1;
    int DEFAULT_LAYER = 0;
    FocusPolicy DEFAULT_FOCUS_POLICY = FocusPolicy.OPAQUE;
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
    int layer() default DEFAULT_LAYER;
    /**
     * When this View is brought into focus, what should happen to the rest
     */
    FocusPolicy focusPolicy() default FocusPolicy.OPAQUE; //Java won't let me do DEFAULT_FOCUS_POLICY = ... and reference that here. :(
}
