package gbw.melange.observance;

import java.util.function.BiFunction;

@FunctionalInterface
public interface EqualityFunction<T> {
    boolean test(T old, T newer);
}
