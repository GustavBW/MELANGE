package gbw.melange.common.events.observability;

/**
 * <p>EqualityFunction interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface EqualityFunction<T> {
    /** Constant <code>DEFAULT</code> */
    EqualityFunction<?> DEFAULT = Object::equals;

    /**
     * <p>test.</p>
     *
     * @param old a T object
     * @param newer a T object
     * @return a boolean
     */
    boolean test(T old, T newer);
}
