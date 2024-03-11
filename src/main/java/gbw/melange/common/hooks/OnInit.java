package gbw.melange.common.hooks;

/**
 * Allows individual elements to fetch external data or other.
 * Additionally, this is managed and failed states and loading states taken care of.
 *
 * TODO: Separate this guy and the VolatileElement content provider. They might have the exact same signature, but this one is fetched by Reflections, which might cause issues for Users defining content providers as classes.
 */
@FunctionalInterface
public interface OnInit<T> {
    T onInit() throws Exception;
}
