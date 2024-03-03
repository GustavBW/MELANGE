package gbw.melange.events.observability;

import gbw.melange.events.observability.filters.IFallibleFilterChain;
import gbw.melange.events.observability.filters.IFilterChain;
import gbw.melange.events.observability.filters.FilterChain;

import java.util.Collection;

public class FallibleObservableValue<T> extends ObservableValue<T, IFallibleBiConsumer<T>>
    implements IFallibleObservableValue<T>{
    private final IFallibleFilterChain<T, Integer> filters = FilterChain.fallible();

    protected FallibleObservableValue(T initialValue){
        super.value = initialValue;
    }

    @Override
    public void set(T newer) throws Exception {
        if(!super.equalityFunction.test(super.value, newer)){
            filters.run(super.value, newer);
        }
        super.value = newer;
    }
    @Override
    public Collection<Exception> setAllowExceptions(T newer) {
        return filters.runAllowExceptions(super.value, newer);
    }
    @Override
    public IFilterChain<IFallibleBiConsumer<T>, Integer> filters() {
        return filters;
    }
}
