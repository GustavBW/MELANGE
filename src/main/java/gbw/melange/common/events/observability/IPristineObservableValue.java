package gbw.melange.common.events.observability;


public interface IPristineObservableValue<T> extends IObservableValue<T, IPristineBiConsumer<T>, Integer> {
    /**
     * Sets the value of the observable. <br/>
     * If the value does not pass the equality function, the filter chain is invoked and run. </br>
     * Null is always ignored
     * @param obj
     */
    void set(T obj);
}
