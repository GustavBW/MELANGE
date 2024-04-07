package gbw.melange.common.events.observability;


/**
 * <p>IPristineObservableValue interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IPristineObservableValue<T> extends IObservableValue<T, IPristineBiConsumer<T>, Integer> {
    /**
     * Sets the value of the observable. <br/>
     * If the value does not pass the equality function, the filter chain is invoked and run. </br>
     * Null is always ignored
     *
     * @param obj a T object
     */
    void set(T obj);
}
