package gbw.melange.events.observability;

import gbw.melange.common.events.observability.IFallibleBiConsumer;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.common.events.observability.filters.IFallibleFilterChain;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.events.observability.filters.FilterChain;

import java.util.Collection;

/**
 * <p>FallibleBlockingObservable class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class FallibleBlockingObservable<T> extends ObservableValue<T, IFallibleBiConsumer<T>>
        implements IFallibleBlockingObservable<T> {
    private final IFallibleFilterChain<T, Integer> filters = FilterChain.fallible();

    private final Object lock = new Object();
    FallibleBlockingObservable(T initialValue){
        super(initialValue);
    }
    /** {@inheritDoc} */
    @Override
    public T get(){
        synchronized (lock){
            return super.get();
        }
    }
    /** {@inheritDoc} */
    @Override
    public void set(T newer) throws Exception {
        if(newer == null) return;
        synchronized (lock) {
            final T current = get();
            if(current == null || !super.equalityFunction.test(current, newer)){
                filters.run(current, newer);
            }
            super.setRaw(newer);
        }
    }
    /** {@inheritDoc} */
    @Override
    public Collection<Exception> setAllowExceptions(T newer) {
        synchronized (lock) {
            final T current = get();
            return filters.runAllowExceptions(current, newer);
        }
    }
    /** {@inheritDoc} */
    @Override
    public IFilterChain<IFallibleBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
