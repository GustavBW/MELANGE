package gbw.melange.events.observability;

@FunctionalInterface
public interface EqualityFunction<T> {
    EqualityFunction<Object> DEFAULT = Object::equals;

    boolean test(T old, T newer);
}
