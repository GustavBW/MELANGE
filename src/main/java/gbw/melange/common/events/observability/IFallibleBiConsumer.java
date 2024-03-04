package gbw.melange.common.events.observability;

@FunctionalInterface
public interface IFallibleBiConsumer<T> extends UndeterminedBiConsumer<T> {

    void accept(T old, T newer) throws Exception;
}
