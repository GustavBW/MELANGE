package gbw.melange.events.observability.filters;

import gbw.melange.events.observability.IPristineBiConsumer;

public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineBiConsumer<T>,R> {
    void run(T old, T newer);

}
