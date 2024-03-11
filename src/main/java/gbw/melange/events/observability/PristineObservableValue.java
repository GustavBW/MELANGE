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
        super(initialValue);
    }

    @Override
    public void set(T newer){
        final T current = super.get();
        if(!super.equalityFunction.test(current, newer)){
            filters.run(current, newer);
        }
        super.setRaw(newer);
    }

    @Override
    public IFilterChain<IPristineBiConsumer<T>, Integer> onChange() {
        return filters;
    }
}
