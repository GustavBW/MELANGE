package gbw.melange.events.observability;

import gbw.melange.common.events.observability.IFallibleBiConsumer;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.common.events.observability.IFallibleObservableValue;
import gbw.melange.common.events.observability.filters.IFallibleFilterChain;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.events.observability.ObservableValue;
import gbw.melange.events.observability.filters.FilterChain;

import java.util.Collection;

public class FallibleBlockingObservable<T> extends ObservableValue<T, IFallibleBiConsumer<T>>
        implements IFallibleBlockingObservable<T> {
    private final IFallibleFilterChain<T, Integer> filters = FilterChain.fallible();

    private final Object lock = new Object();
    FallibleBlockingObservable(T initialValue){
        super(initialValue);
    }
    @Override
    public T get(){
        synchronized (lock){
            return super.get();
        }
    }
    @Override
    public void set(T newer) throws Exception {
        if(newer == null) return;
        synchronized (lock) {
            final T current = get();
            if(!super.equalityFunction.test(current, newer)){
                filters.run(current, newer);
            }
            super.setRaw(newer);
        }
    }
    @Override
    public Collection<Exception> setAllowExceptions(T newer) {
        synchronized (lock) {
            final T current = get();
            return filters.runAllowExceptions(current, newer);
        }
    }
    @Override
    public IFilterChain<IFallibleBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
