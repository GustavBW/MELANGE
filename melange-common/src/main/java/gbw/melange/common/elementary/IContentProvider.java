package gbw.melange.common.elementary;

/**
 * <p>IContentProvider interface.</p>
 *
 * @author lilybw
 * @version $Id: $Id
 */
@FunctionalInterface
public interface IContentProvider<T> {
    /**
     * <p>fetch.</p>
     *
     * @return a T object
     * @throws java.lang.Exception if any.
     */
    T fetch() throws Exception;
}
