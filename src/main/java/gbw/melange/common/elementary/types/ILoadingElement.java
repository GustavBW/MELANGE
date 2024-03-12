package gbw.melange.common.elementary.types;

import gbw.melange.common.hooks.OnInit;

public interface ILoadingElement<T> extends IElement<T> {
    void setContent(T value) throws Exception;
    void invokeProvider() throws Exception;
}
