package gbw.melange.observance;

import java.util.function.BiFunction;

@FunctionalInterface
public interface EqualityFunction<T> {
    EqualityFunction<Object> DEFAULT = Object::equals;

    boolean test(T old, T newer);
}
