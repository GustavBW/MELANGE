package gbw.melange.observance.filters;

import gbw.melange.observance.IPristineBiConsumer;

public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineBiConsumer<T>,R> {
    void run(T old, T newer);

}
