package gbw.melange.common.events.observability.filters;

import gbw.melange.common.events.observability.IPristineBiConsumer;

public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineBiConsumer<T>,R> {
    void run(T old, T newer);

}
