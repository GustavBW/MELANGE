package gbw.melange.observance.filters;

import gbw.melange.observance.FailingOnChangeConsumer;

public interface IFallibleFilterChain<T,R> extends IFilterChain<FailingOnChangeConsumer<T>,R> {
    void run(T obj) throws Exception;
    void runAllowExceptions(T obj);
}
