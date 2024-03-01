package gbw.melange.observance.filters;

import gbw.melange.observance.IPristineOnChangeConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Intercepting Filter Patten
 * @param <T>
 */
public class PristineOnChangeFilterChain<T> implements IPristineFilterChain<T,Integer> {



    private final List<IPristineOnChangeConsumer<T>> filters = new ArrayList<>();

    public static <T> IPristineFilterChain<T,Integer> create(){

        return new PristineOnChangeFilterChain<>();
    }


    @Override
    public Integer addFilter(IPristineOnChangeConsumer<T> filter) {
        return null;
    }

    @Override
    public Integer addFilter(IPristineOnChangeConsumer<T> filter, Integer identifier) {
        return null;
    }

    @Override
    public boolean replaceFilter(Integer identifier, IPristineOnChangeConsumer<T> filter) {
        return false;
    }

    @Override
    public boolean replaceFilter(IPristineOnChangeConsumer<T> filter) {
        return false;
    }

    @Override
    public boolean removeFilter(IPristineOnChangeConsumer<T> filter) {
        return false;
    }

    @Override
    public boolean removeOnId(Integer identifier) {
        return false;
    }

    @Override
    public void run(T obj) {

    }
}
