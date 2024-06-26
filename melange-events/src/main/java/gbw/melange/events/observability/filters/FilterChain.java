package gbw.melange.events.observability.filters;

import gbw.melange.common.events.observability.UndeterminedBiConsumer;
import gbw.melange.common.events.observability.filters.IFallibleFilterChain;
import gbw.melange.common.events.observability.filters.IFilterChain;
import gbw.melange.common.events.observability.filters.IPristineFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Entrypoint for retrieving FilterChain implementations. Both pristine and fallible.
 *
 * @param <T> Type of the type the consumers (filters) consume
 * @param <R> Actual type of the consumer itself
 *
 * TODO: Refactor to ServiceLoader use when modularized
 * @author GustavBW
 * @version $Id: $Id
 */
public abstract class FilterChain<T, R extends UndeterminedBiConsumer<T>>
        implements IFilterChain<R, Integer> {

    protected final class FilterIdPair{
        private R filter;
        private final Integer id;

        FilterIdPair(R filter, Integer id) {
            this.filter = filter;
            this.id = id;
        }

        public R filter() {
            return filter;
        }
        public void setFilter(R filter){
            this.filter = filter;
        }

        public Integer id() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            try{
                if(((FilterIdPair) obj).id().equals(this.id())) return true;
            }catch (ClassCastException ignored){
                return false;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(filter, id);
        }

        @Override
        public String toString() {
            return "FilterIdPair[" +
                    "filter=" + filter + ", " +
                    "id=" + id + ']';
        }
    }
    protected final List<FilterIdPair> filters = new ArrayList<>();
    private int latestId = 0;

    /**
     * <p>pristine.</p>
     *
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.filters.IPristineFilterChain} object
     */
    public static <T> IPristineFilterChain<T,Integer> pristine(){
        return new PristineFilterChain<>();
    }
    /**
     * <p>fallible.</p>
     *
     * @param <T> a T class
     * @return a {@link gbw.melange.common.events.observability.filters.IFallibleFilterChain} object
     */
    public static <T> IFallibleFilterChain<T, Integer> fallible(){
        return new FallibleFilterChain<>();
    }

    /**
     * <p>getNextId.</p>
     *
     * @return a int
     */
    protected int getNextId(){
        return latestId++;
    }

    /** {@inheritDoc} */
    @Override
    public Integer addFilter(R filter) {
        int idOfThis = getNextId();
        filters.add(new FilterIdPair(filter, idOfThis));
        return idOfThis;
    }
    /** {@inheritDoc} */
    @Override
    public Integer addFilter(R filter, Integer identifier) {
        if(containsById(identifier)){
            return addFilter(filter);
        }
        filters.add(new FilterIdPair(filter, identifier));
        return identifier;
    }
    /** {@inheritDoc} */
    @Override
    public boolean removeOnId(Integer identifier) {
        FilterIdPair found = findFirst(identifier);
        if(found != null){
            return filters.remove(found);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsById(Integer identifier) {
        return findFirst(identifier) != null;
    }
    /** {@inheritDoc} */
    @Override
    public boolean contains(R filter) {
        return findFirst(filter) != null;
    }
    /** {@inheritDoc} */
    @Override
    public boolean removeFilter(R filter) {
        FilterIdPair found = findFirst(filter);
        if(found != null){
            return filters.remove(found);
        }
        return false;
    }
    /** {@inheritDoc} */
    @Override
    public boolean replaceFilter(Integer identifier, R filter) {
        FilterIdPair found = findFirst(identifier);
        if(found != null){
            found.setFilter(filter);
            return true;
        }
        return false;
    }
    /**
     * <p>findFirst.</p>
     *
     * @param filter a R object
     * @return a {@link FilterChain.FilterIdPair} object
     */
    protected FilterIdPair findFirst(R filter){
        return findFirst(pair -> filter == pair.filter());
    }
    /**
     * <p>findFirst.</p>
     *
     * @param id a {@link java.lang.Integer} object
     * @return a {@link FilterChain.FilterIdPair} object
     */
    protected FilterIdPair findFirst(Integer id){
        return findFirst(pair -> Objects.equals(pair.id(), id));
    }
    /**
     * <p>findFirst.</p>
     *
     * @param condition a {@link java.util.function.Predicate} object
     * @return a {@link FilterChain.FilterIdPair} object
     */
    protected FilterIdPair findFirst(Predicate<FilterIdPair> condition){
        for(FilterIdPair pair : filters){
            if(condition.test(pair)){
                return pair;
            }
        }
        return null;
    }
}
