package gbw.melange.observance.filters;

import gbw.melange.observance.IFallibleBiConsumer;

import java.util.Collection;

public interface IFallibleFilterChain<T,R> extends IFilterChain<IFallibleBiConsumer<T>,R> {
    void run(T old, T newer) throws Exception;

    /**
     * @return Any exceptions that occurred during setting the value or running the filter chain if invoked.
     */
    Collection<Exception> runAllowExceptions(T old, T newer);
}
