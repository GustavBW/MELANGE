package gbw.melange.common.events.observability.filters;

import gbw.melange.common.events.observability.IFallibleBiConsumer;

import java.util.Collection;

/**
 * <p>IFallibleFilterChain interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IFallibleFilterChain<T,R> extends IFilterChain<IFallibleBiConsumer<T>,R> {
    /**
     * <p>run.</p>
     *
     * @param old a T object
     * @param newer a T object
     * @throws java.lang.Exception if any.
     */
    void run(T old, T newer) throws Exception;

    /**
     * <p>runAllowExceptions.</p>
     *
     * @return Any exceptions that occurred during setting the value or running the filter chain if invoked.
     * @param old a T object
     * @param newer a T object
     */
    Collection<Exception> runAllowExceptions(T old, T newer);
}
