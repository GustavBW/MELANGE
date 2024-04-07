package gbw.melange.common.events.observability;

import java.util.Collection;

/**
 * <p>IFallibleObservableValue interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IFallibleObservableValue<T> extends IObservableValue<T, IFallibleBiConsumer<T>, Integer> {
    /**
     * Sets the value of the observable. <br/>
     * If the value does not pass the equality function, the filter chain is invoked and run.</br>
     * Null is always ignored
     *
     * @param newer a T object
     * @throws java.lang.Exception if any.
     */
    void set(T newer) throws Exception;

    /**
     * <p>setAllowExceptions.</p>
     *
     * @return Any exceptions that occurred during setting the value or running the filter chain if invoked.
     * @param newer a T object
     */
    Collection<Exception> setAllowExceptions(T newer);
}
