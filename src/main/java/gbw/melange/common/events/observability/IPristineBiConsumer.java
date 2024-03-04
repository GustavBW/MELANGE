package gbw.melange.common.events.observability;

/**
 * Promises not to throw exceptions during runtime.
 * @param <T>
 */
@FunctionalInterface
public interface IPristineBiConsumer<T> extends UndeterminedBiConsumer<T> {
    void accept(T old, T newer);
}
