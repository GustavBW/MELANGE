package gbw.melange.common.builders;

public interface IBuilder<T> {

    /**
     * @return A new instance.
     */
    T build();
}
