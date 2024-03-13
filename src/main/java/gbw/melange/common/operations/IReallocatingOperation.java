package gbw.melange.common.operations;

/**
 * <p>IReallocatingOperation interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface IReallocatingOperation<T,R> {
    /**
     * <p>apply.</p>
     *
     * @param t a T object
     * @return a R object
     */
    R apply(T t);
}
