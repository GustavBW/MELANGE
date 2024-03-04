package gbw.melange.events.observability;

import gbw.melange.common.events.observability.IPristineBiConsumer;
import gbw.melange.common.events.observability.IPristineObservableValue;
import gbw.melange.events.observability.filters.FilterChain;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.common.events.observability.filters.IPristineFilterChain;

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
    public IFilterChain<IPristineBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
