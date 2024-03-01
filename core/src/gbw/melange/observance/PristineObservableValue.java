package gbw.melange.observance;

import gbw.melange.observance.filters.FilterChain;
import gbw.melange.observance.filters.IFilterChain;
import gbw.melange.observance.filters.IPristineFilterChain;
import gbw.melange.observance.filters.PristineFilterChain;

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
