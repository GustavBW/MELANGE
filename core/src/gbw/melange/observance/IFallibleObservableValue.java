package gbw.melange.observance;

import java.util.Collection;

public interface IFallibleObservableValue<T> extends IObservableValue<T, IFallibleBiConsumer<T>, Integer> {
    /**
     * Sets the value of the observable. <br/>
     * If the value does not pass the equality function, the filter chain is invoked and run.
     */
    void set(T newer) throws Exception;

    /**
     * @return Any exceptions that occurred during setting the value or running the filter chain if invoked.
     */
    Collection<Exception> setAllowExceptions(T newer);
}
