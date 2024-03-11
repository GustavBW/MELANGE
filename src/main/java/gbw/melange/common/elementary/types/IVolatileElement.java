package gbw.melange.common.elementary.types;

public interface IVolatileElement<T> extends IElement<T> {
    void setContent(T value) throws Exception;
}
