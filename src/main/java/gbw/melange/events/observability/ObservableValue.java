package gbw.melange.events.observability;

/**
 * Abstract Factory pattern. Also happens to contain some shared functionality and extend this to the subclasses.
 * @param <T>
 * @param <R>
 */
public abstract class ObservableValue<T, R extends UndeterminedBiConsumer<T>> implements IObservableValue<T, R, Integer> {
    protected EqualityFunction<T> equalityFunction = (EqualityFunction<T>) EqualityFunction.DEFAULT;
    protected T value;
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

    @Override
    public T get(){
        return value;
    }

    @Override
    public void changeEqualityFunction(EqualityFunction<T> predicate){
        this.equalityFunction = predicate;
    }


}
