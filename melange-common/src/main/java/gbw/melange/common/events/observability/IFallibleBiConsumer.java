package gbw.melange.common.events.observability;

/**
 * <p>IFallibleBiConsumer interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface IFallibleBiConsumer<T> extends UndeterminedBiConsumer<T> {

    /**
     * <p>accept.</p>
     *
     * @param old a T object
     * @param newer a T object
     * @throws java.lang.Exception if any.
     */
    void accept(T old, T newer) throws Exception;
}
