package gbw.melange.common.operations;

/**
 * <p>IInPlaceOperation interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface IInPlaceOperation<T> {
    /**
     * <p>apply operation.</p>
     *
     * @param t a T object
     */
    void apply(T t);
}
