package gbw.melange.core.observance;

import gbw.melange.core.observance.filters.FilterChain;
import gbw.melange.core.observance.filters.IFilterChain;
import gbw.melange.core.observance.filters.IPristineFilterChain;

public class PristineObservableValue<T> extends ObservableValue<T, IPristineBiConsumer<T>>
        implements IPristineObservableValue<T> {

    private final IPristineFilterChain<T, Integer> filters = FilterChain.pristine();

    protected PristineObservableValue(T initialValue){
        super.value = initialValue;
    }

    @Override
    public void set(T newer){
        if(!super.equalityFunction.test(value, newer)){
            filters.run(value, newer);
        }
        this.value = newer;
    }

    @Override
    public IFilterChain<IPristineBiConsumer<T>, Integer> filters() {
        return filters;
    }
}
