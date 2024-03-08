package gbw.melange.common.operations;

@FunctionalInterface
public interface IReallocatingOperation<T,R> {
    R apply(T t);
}
