package gbw.melange.observance;

/**
 * Promises not to throw exceptions during runtime.
 * @param <T>
 */
@FunctionalInterface
public interface IPristineOnChangeConsumer<T> extends Action<T> {
    void accept(T old, T newer);
}
