package gbw.melange.core.observance.filters;

import gbw.melange.core.observance.IPristineBiConsumer;

public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineBiConsumer<T>,R> {
    void run(T old, T newer);

}
