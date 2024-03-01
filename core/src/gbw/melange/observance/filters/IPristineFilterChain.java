package gbw.melange.observance.filters;

import gbw.melange.observance.IPristineOnChangeConsumer;

public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineOnChangeConsumer<T>,R> {
    void run(T obj);

}
