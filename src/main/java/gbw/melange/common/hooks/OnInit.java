package gbw.melange.common.hooks;

/**
 * Allows individual elements to fetch external data or other.
 * Additionally, this is managed and failed states and loading states taken care of.
 */
@FunctionalInterface
public interface OnInit<T> {
    T onInit() throws Exception;
}
