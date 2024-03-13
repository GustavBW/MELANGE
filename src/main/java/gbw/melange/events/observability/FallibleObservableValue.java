package gbw.melange.events.observability;

import gbw.melange.common.events.observability.IFallibleBiConsumer;
import gbw.melange.common.events.observability.IFallibleObservableValue;
import gbw.melange.common.events.observability.filters.IFallibleFilterChain;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.events.observability.filters.FilterChain;

import java.util.Collection;

/**
 * <p>FallibleObservableValue class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class FallibleObservableValue<T> extends ObservableValue<T, IFallibleBiConsumer<T>>
    implements IFallibleObservableValue<T> {
    private final IFallibleFilterChain<T, Integer> filters = FilterChain.fallible();

    /**
     * <p>Constructor for FallibleObservableValue.</p>
     *
     * @param initialValue a T object
     */
    protected FallibleObservableValue(T initialValue){
        super(initialValue);
    }

    /** {@inheritDoc} */
    @Override
    public void set(T newer) throws Exception {
        if(newer == null) return;
        final T current = super.get();
        if(!super.equalityFunction.test(current, newer)){
            filters.run(current, newer);
        }
        super.setRaw(newer);
    }
    /** {@inheritDoc} */
    @Override
    public Collection<Exception> setAllowExceptions(T newer) {
        return filters.runAllowExceptions(super.get(), newer);
    }
    /** {@inheritDoc} */
    @Override
    public IFilterChain<IFallibleBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
