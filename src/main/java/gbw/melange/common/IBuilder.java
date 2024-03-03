package gbw.melange.common;

public interface IBuilder<T> {

    /**
     * @return A new instance.
     */
    T build();
}
