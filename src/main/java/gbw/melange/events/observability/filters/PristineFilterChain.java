package gbw.melange.events.observability.filters;

import gbw.melange.common.events.observability.IPristineBiConsumer;
import gbw.melange.common.events.observability.filters.IPristineFilterChain;

/**
 * Intercepting Filter Patten
 * @param <T> Type of the type the consumers (filters) consume
 */
public class PristineFilterChain<T>
        extends FilterChain<T, IPristineBiConsumer<T>>
        implements IPristineFilterChain<T, Integer>
{
    protected PristineFilterChain(){}

    @Override
    public void run(T old, T newer) {
        for(FilterIdPair filterIdPair : filters){
            filterIdPair.filter().accept(old, newer);
        }
    }


}
