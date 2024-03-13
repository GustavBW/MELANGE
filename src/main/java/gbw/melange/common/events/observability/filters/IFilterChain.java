package gbw.melange.common.events.observability.filters;

/**
 * <p>IFilterChain interface.</p>
 *
 * @param <T> type of filter, either failing, which is okay, or pristine.
 * @param <R> type of identifier for each filter, managed by the given IFilterChain implementation. If not explicitly given, the equality function used between identifiers will be object equivalence.
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IFilterChain<T, R> {
    /**
     * Adds a filter to the chain
     *
     * @return the assigned identifier for that filter.
     * @param filter a T object
     */
    R addFilter(T filter);

    /**
     * When trying to add the filter to the chain, the identifier specified might be occupied in which case this method will do nothing. <br/>
     * To replace an existing filter, use {@link gbw.melange.common.events.observability.filters.IFilterChain#replaceFilter(R, T)} instead
     *
     * @return the resulting identifier
     * @param filter a T object
     * @param identifier a R object
     */
    R addFilter(T filter, R identifier);

    /**
     * Replaces an existing filter by the given id if it exists.
     *
     * @return Whether a filter was replaced or not.
     * @param identifier a R object
     * @param filter a T object
     */
    boolean replaceFilter(R identifier, T filter);

    /**
     * <p>removeFilter.</p>
     *
     * @return Whether a filter was removed or not.
     * @param filter a T object
     */
    boolean removeFilter(T filter);

    /**
     * <p>removeOnId.</p>
     *
     * @return Whether a filter was removed or not.
     * @param identifier a R object
     */
    boolean removeOnId(R identifier);

    /**
     * <p>contains.</p>
     *
     * @return True if the filter chain currently contains the provider filter.
     * @param filter a T object
     */
    boolean contains(T filter);
    /**
     * <p>containsById.</p>
     *
     * @return True if the filter chain currently contains a filter with that id.
     * @param identifier a R object
     */
    boolean containsById(R identifier);
}
