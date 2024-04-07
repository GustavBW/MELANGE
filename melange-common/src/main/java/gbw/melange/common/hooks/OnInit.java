package gbw.melange.common.hooks;

/**
 * Allows individual elements to fetch external data or other.
 * Additionally, this is managed and failed states and loading states taken care of.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface OnInit<T> {
    /**
     * <p>onInit.</p>
     *
     * @return a T object
     * @throws java.lang.Exception if any.
     */
    T onInit() throws Exception;
}
