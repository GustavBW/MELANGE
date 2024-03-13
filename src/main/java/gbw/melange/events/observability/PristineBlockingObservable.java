package gbw.melange.events.observability;

import gbw.melange.common.events.observability.IPrestineBlockingObservable;
import gbw.melange.common.events.observability.IPristineBiConsumer;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.common.events.observability.filters.IPristineFilterChain;
import gbw.melange.events.observability.filters.FilterChain;

/**
 * <p>PristineBlockingObservable class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class PristineBlockingObservable<T> extends ObservableValue<T, IPristineBiConsumer<T>>
        implements IPrestineBlockingObservable<T> {

    private final IPristineFilterChain<T, Integer> filters = FilterChain.pristine();

    private final Object lock = new Object();
    PristineBlockingObservable(T initialValue){
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
    public void set(T newer){
        if(newer == null){
            return;
        }
        synchronized (lock) {
            final T current = get();
            if(!super.equalityFunction.test(current, newer)){
                filters.run(current, newer);
            }
            super.setRaw(newer);
        }
    }

    /** {@inheritDoc} */
    @Override
    public IFilterChain<IPristineBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
