package gbw.melange.core.observance;

@FunctionalInterface
public interface EqualityFunction<T> {
    EqualityFunction<Object> DEFAULT = Object::equals;

    boolean test(T old, T newer);
}
