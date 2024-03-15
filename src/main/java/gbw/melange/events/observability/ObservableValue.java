package gbw.melange.events.observability;

import gbw.melange.common.events.observability.*;

/**
 * Abstract Factory pattern. Also happens to contain some shared functionality and extend this to the subclasses.
 *
 * @param <T> Type of value being observed
 * @param <R> The OnChange bi consumers that can be appended.
 * @author GustavBW
 * @version $Id: $Id
 */
public abstract class ObservableValue<T, R extends UndeterminedBiConsumer<T>> implements IObservableValue<T, R, Integer> {

    protected static class DefaultNoEquality<T> implements EqualityFunction<T> {
        @Override
        public boolean test(Object o, Object newer) {
            return false;
        }
    }

    protected EqualityFunction<T> equalityFunction = new DefaultNoEquality<>();
    private T value;

    /**
     * <p>pristine.</p>
     *
     * @param initialValue a T object
     * @param equalityFunction a {@link gbw.melange.common.events.observability.EqualityFunction} object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IPristineObservableValue} object
     */
    public static <T> IPristineObservableValue<T> pristine(T initialValue, EqualityFunction<T> equalityFunction){
        IPristineObservableValue<T> instance = new PristineObservableValue<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }

    /**
     * <p>fallible.</p>
     *
     * @param initialValue a T object
     * @param equalityFunction a {@link gbw.melange.common.events.observability.EqualityFunction} object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IFallibleObservableValue} object
     */
    public static <T> IFallibleObservableValue<T> fallible(T initialValue, EqualityFunction<T> equalityFunction){
        IFallibleObservableValue<T> instance = new FallibleObservableValue<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }
    /**
     * <p>blockingFallible.</p>
     *
     * @param initialValue a T object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IFallibleBlockingObservable} object
     */
    public static <T> IFallibleBlockingObservable<T> blockingFallible(T initialValue) {
        return blockingFallible(initialValue, (EqualityFunction<T>) EqualityFunction.DEFAULT);
    }
    /**
     * <p>blockingFallible.</p>
     *
     * @param initialValue a T object
     * @param equalityFunction a {@link gbw.melange.common.events.observability.EqualityFunction} object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IFallibleBlockingObservable} object
     */
    public static <T> IFallibleBlockingObservable<T> blockingFallible(T initialValue, EqualityFunction<T> equalityFunction){
        IFallibleBlockingObservable<T> instance = new FallibleBlockingObservable<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }
    /**
     * <p>blockingPristine.</p>
     *
     * @param initialValue a T object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IPrestineBlockingObservable} object
     */
    public static <T> IPrestineBlockingObservable<T> blockingPristine(T initialValue){
        return blockingPristine(initialValue, (EqualityFunction<T>) EqualityFunction.DEFAULT);
    }
    /**
     * <p>blockingPristine.</p>
     *
     * @param initialValue a T object
     * @param equalityFunction a {@link gbw.melange.common.events.observability.EqualityFunction} object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.IPrestineBlockingObservable} object
     */
    public static <T> IPrestineBlockingObservable<T> blockingPristine(T initialValue, EqualityFunction<T> equalityFunction){
        IPrestineBlockingObservable<T> instance = new PristineBlockingObservable<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }

    /**
     * <p>Constructor for ObservableValue.</p>
     *
     * @param initialValue a T object
     */
    protected ObservableValue(T initialValue){
        this.value = initialValue;
    }

    /** {@inheritDoc} */
    @Override
    public T get(){
        return value;
    }

    /**
     * <p>setRaw.</p>
     *
     * @param newer a T object
     */
    protected void setRaw(T newer){
        value = newer;
    }

    /** {@inheritDoc} */
    @Override
    public void changeEqualityFunction(EqualityFunction<T> predicate){
        this.equalityFunction = predicate;
    }


}
