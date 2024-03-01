package gbw.melange.observance.filters;

import gbw.melange.observance.IFallibleBiConsumer;

import java.util.ArrayList;
import java.util.Collection;

public class FallibleFilterChain<T> extends FilterChain<T, IFallibleBiConsumer<T>>
        implements IFallibleFilterChain<T, Integer> {

    protected FallibleFilterChain(){}

    @Override
    public void run(T old, T newer) throws Exception {
        for(FilterIdPair pair : filters){
            pair.filter().accept(old, newer);
        }
    }

    @Override
    public Collection<Exception> runAllowExceptions(T old, T newer) {
        Collection<Exception> issues = new ArrayList<>();
        for(FilterIdPair pair : filters){
            try{
                pair.filter().accept(old, newer);
            }catch (Exception returned){
                issues.add(returned);
            }
        }
        return issues;
    }

}
