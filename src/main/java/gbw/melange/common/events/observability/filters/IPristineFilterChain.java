package gbw.melange.common.events.observability.filters;

import gbw.melange.common.events.observability.IPristineBiConsumer;

/**
 * <p>IPristineFilterChain interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IPristineFilterChain<T,R> extends IFilterChain<IPristineBiConsumer<T>,R> {
    /**
     * <p>run.</p>
     *
     * @param old a T object
     * @param newer a T object
     */
    void run(T old, T newer);

}
