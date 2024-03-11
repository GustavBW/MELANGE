package gbw.melange.common.elementary.types;

import gbw.melange.common.hooks.OnInit;

public interface ILoadingElement<T> extends IElement<T>, OnInit<T> {
    void setContent(T value) throws Exception;
}
