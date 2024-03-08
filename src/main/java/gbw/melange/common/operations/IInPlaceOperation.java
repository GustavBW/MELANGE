package gbw.melange.common.operations;

@FunctionalInterface
public interface IInPlaceOperation<T> {
    void apply(T t);
}
