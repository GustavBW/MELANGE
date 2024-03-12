package gbw.melange.events.observability;

import gbw.melange.common.events.observability.*;

/**
 * Abstract Factory pattern. Also happens to contain some shared functionality and extend this to the subclasses.
 * @param <T> Type of value being observed
 * @param <R> The OnChange bi consumers that can be appended.
 * TODO: Refactor to ServiceLoader use when modularized
 */
public abstract class ObservableValue<T, R extends UndeterminedBiConsumer<T>> implements IObservableValue<T, R, Integer> {
    protected EqualityFunction<T> equalityFunction = (any1, any2) -> false;
    private T value;

    public static <T> IPristineObservableValue<T> pristine(T initialValue, EqualityFunction<T> equalityFunction){
        IPristineObservableValue<T> instance = new PristineObservableValue<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }

    public static <T> IFallibleObservableValue<T> fallible(T initialValue, EqualityFunction<T> equalityFunction){
        IFallibleObservableValue<T> instance = new FallibleObservableValue<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }
    public static <T> IFallibleBlockingObservable<T> blockingFallible(T initialValue) {
        return blockingFallible(initialValue, (EqualityFunction<T>) EqualityFunction.DEFAULT);
    }
    public static <T> IFallibleBlockingObservable<T> blockingFallible(T initialValue, EqualityFunction<T> equalityFunction){
        IFallibleBlockingObservable<T> instance = new FallibleBlockingObservable<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }
    public static <T> IPrestineBlockingObservable<T> blockingPristine(T initialValue){
        return blockingPristine(initialValue, (EqualityFunction<T>) EqualityFunction.DEFAULT);
    }
    public static <T> IPrestineBlockingObservable<T> blockingPristine(T initialValue, EqualityFunction<T> equalityFunction){
        IPrestineBlockingObservable<T> instance = new PristineBlockingObservable<>(initialValue);
        instance.changeEqualityFunction(equalityFunction);
        return instance;
    }

    protected ObservableValue(T initialValue){
        this.value = initialValue;
    }

    @Override
    public T get(){
        return value;
    }

    protected void setRaw(T newer){
        value = newer;
    }

    @Override
    public void changeEqualityFunction(EqualityFunction<T> predicate){
        this.equalityFunction = predicate;
    }


}
