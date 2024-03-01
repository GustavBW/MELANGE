package gbw.melange.observance;

@FunctionalInterface
public interface FailingOnChangeConsumer<T> extends Action<T> {

    void accept(T old, T newer) throws Exception;
}
