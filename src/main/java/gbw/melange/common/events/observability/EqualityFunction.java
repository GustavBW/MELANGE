package gbw.melange.common.events.observability;

@FunctionalInterface
public interface EqualityFunction<T> {
    EqualityFunction<?> DEFAULT = Object::equals;

    boolean test(T old, T newer);
}
