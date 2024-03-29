package gbw.melange.common.events.observability;

import gbw.melange.common.events.observability.filters.IFilterChain;

/**
 * An Observer pattern employing an Intercepting Filters pattern to handle observers.
 *
 * @param <T> Type of value held by this observable
 * @param <R> Type of on change filters applicable to this observable
 * @param <U> Type of identifier for each filter.
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IObservableValue<T, R extends UndeterminedBiConsumer<T>, U> {
    /**
     * Retrieves the current value.
     *
     * @return a T object
     */
    T get();

    /**
     * The filter chain is only run when this function evaluates to false. <br/>
     * Meaning providing this function with (old, newer) -> true will have it run everytime the
     * value is set.
     *
     * @param newFunc a {@link gbw.melange.common.events.observability.EqualityFunction} object
     */
    void changeEqualityFunction(EqualityFunction<T> newFunc);

    /**
     * <p>onChange.</p>
     *
     * @return Returns the on change consumers applied to this observable value.
     */
    IFilterChain<R,U> onChange();
}
