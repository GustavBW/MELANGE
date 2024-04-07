package gbw.melange.common.mesh.formatting.slicing;

import gbw.melange.common.jpms.SPILocator;
import gbw.melange.common.mesh.formatting.providers.SliceProvider;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Represents a slice of a float array for efficient, non-boxing access to a range of floats.
 * This class is designed for performance-sensitive applications where direct access to
 * the underlying array's elements is required without the overhead of boxing.<br/>
 * This is but the parent interface for many, vastly more usefull representations such as
 * {@link ISliceVec2}, {@link ISliceVec3}, and {@link ISliceVec4} which imitates the behaviour of a vector.<br/>
 * Note: The implementations does not support spliterator operations. Attempting to
 * call {@link #spliterator()} will result in a {@link RuntimeException}.
 *
 * @author GustavBW
 */
public interface IFloatSlice extends Iterable<Float> {



    /**
     * Number of components in this reference access vector
     */
    int entryWidth();
    /**
     * When the series of vector representations was retrieved from the buffer,
     * that this is a part of, what is the index of the first component of this.
     */
    int indexFirstComp();

    /**
     * Get the value at the index of the slice.
     * Will throw an {@link ArrayIndexOutOfBoundsException} if you try and access indexes not within
     * this specific slice.
     * @return float
     */
    float get(int index);
    /**
     * Set the value at some index in the source through this slice.
     * @param index of this slice
     * @param value to set
     */
    void set(int index, float value);
    /**
     * Any function taking in an unboxed float.
     * <pre>
     *     {@code float -> any || Class::method, where args in method: (float name)}
     * </pre>
     */
    @FunctionalInterface
    interface FloatConsumer {
        void accept(float value);
    }

    /**
     * Iterate over each element in the slice using a method reference / lambda.
     * Do be aware of the initialization cost of lambdas, so if used in a hot path,
     * allocate the lambda outside of it or implement a method with signature:
     * <pre>
     *     {@code <any> void methodName(float variableName) {...} }
     * </pre>
     * And reference it from your code using fx. {@code this::methodName} if defined within the class
     * where you're calling {@link IFloatSlice#forEachFloat(FloatConsumer)}
     * @param func What to do with each float
     */
    void forEachFloat(FloatConsumer func);

    IFloatSlice copy();

    /**
     * Don't use this, as this is the generic version and will cause boxing, but is required by the Iterable interface.
     * Instead, use {@link IFloatSlice#forEachFloat(FloatConsumer)}.
     * You can help Java sort out its issues by declaring a lambda as of type FloatConsumer, or casting any method reference or lambda.
     */
    @Override
    void forEach(Consumer<? super Float> action);

    /**
     * Absolutely not supported. Will throw an exception if invoked.
     * This also rules out processing a single slice using parallel streams.
     * @throw {@link RuntimeException}
     * @return nothing
     */
    @Override
    Spliterator<Float> spliterator();
}
