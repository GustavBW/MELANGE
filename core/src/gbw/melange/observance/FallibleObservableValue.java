package gbw.melange.observance;

import gbw.melange.observance.filters.FilterChain;
import gbw.melange.observance.filters.IFallibleFilterChain;
import gbw.melange.observance.filters.IFilterChain;
import gbw.melange.observance.filters.IPristineFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
