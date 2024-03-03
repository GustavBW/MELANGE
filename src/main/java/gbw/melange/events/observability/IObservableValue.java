package gbw.melange.events.observability;

import gbw.melange.events.observability.filters.IFilterChain;

/**
 * An Observer pattern employing an Intercepting Filters pattern to handle observers.
 * @param <T> Type of value held by this observable
 * @param <R> Type of on change filters applicable to this observable
 * @param <U> Type of identifier for each filter.
 */
public interface IObservableValue<T, R extends UndeterminedBiConsumer<T>, U> {
    /**
     * Retrieves the current value.
     */
    T get();

    /**
     * The filter chain is only run when this function evaluates to false. <br/>
     * Meaning providing this function with (old, newer) -> true will have it run everytime the
     * value is set.
     */
    void changeEqualityFunction(EqualityFunction<T> newFunc);

    /**
     * @return Returns the on change consumers applied to this observable value.
     */
    IFilterChain<R,U> onChange();
}
