package gbw.melange.common.events.observability;

/**
 * Promises not to throw exceptions during runtime.
 *
 * @param <T>
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface IPristineBiConsumer<T> extends UndeterminedBiConsumer<T> {
    /**
     * <p>accept.</p>
     *
     * @param old a T object
     * @param newer a T object
     */
    void accept(T old, T newer);
}
