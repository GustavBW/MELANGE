package gbw.melange.core.observance;

import gbw.melange.core.observance.filters.IFilterChain;

/**
 *
 * @param <T> Type of value held by this observable
 * @param <R> Type of on change filters applicable to this observable
 * @param <U> Type of identifier for each filter.
 */
public interface IObservableValue<T, R extends UndeterminedBiConsumer<T>, U> {
    T get();


    void changeEqualityFunction(EqualityFunction<T> newFunc);

    /**
     * @return Returns the on change consumers applied to this observable value.
     */
    IFilterChain<R,U> filters();
}
