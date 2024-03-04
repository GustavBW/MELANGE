package gbw.melange.common.events.observability.filters;

/**
 *
 * @param <T> type of filter, either failing, which is okay, or pristine.
 * @param <R> type of identifier for each filter, managed by the given IFilterChain implementation. If not explicitly given, the equality function used between identifiers will be object equivalence.
 */
public interface IFilterChain<T, R> {
    /**
     * Adds a filter to the chain
     * @return the assigned identifier for that filter.
     */
    R addFilter(T filter);

    /**
     * When trying to add the filter to the chain, the identifier specified might be occupied in which case this method will do nothing. <br/>
     * To replace an existing filter, use {@link IFilterChain#replaceFilter(R, T)} instead
     * @return the resulting identifier
     */
    R addFilter(T filter, R identifier);

    /**
     * Replaces an existing filter by the given id if it exists.
     * @return Whether a filter was replaced or not.
     */
    boolean replaceFilter(R identifier, T filter);

    /**
     * @return Whether a filter was removed or not.
     */
    boolean removeFilter(T filter);

    /**
     * @return Whether a filter was removed or not.
     */
    boolean removeOnId(R identifier);

    /**
     * @return True if the filter chain currently contains the provider filter.
     */
    boolean contains(T filter);
    /**
     * @return True if the filter chain currently contains a filter with that id.
     */
    boolean containsById(R identifier);
}
