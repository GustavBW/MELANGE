package gbw.melange.common.elementary.types;

import gbw.melange.common.hooks.OnInit;

/**
 * <p>ILoadingElement interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ILoadingElement<T> extends IElement<T> {
    /**
     * <p>setContent.</p>
     *
     * @param value a T object
     * @throws java.lang.Exception if any.
     */
    void setContent(T value) throws Exception;
    /**
     * <p>invokeProvider.</p>
     *
     * @throws java.lang.Exception if any.
     */
    void invokeProvider() throws Exception;
}
