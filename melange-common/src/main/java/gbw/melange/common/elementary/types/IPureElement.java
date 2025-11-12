package gbw.melange.common.elementary.types;

/**
 * <p>IPureElement interface.</p>
 *
 * @author lilybw
 * @version $Id: $Id
 */
public interface IPureElement<T> extends IElement<T>{
    /**
     * <p>setContent.</p>
     *
     * @param value a T object
     */
    void setContent(T value);
}
