package gbw.melange.common.elementary.contraints;

import gbw.melange.common.elementary.types.IElement;


/**
 * <p>IElementConstraints interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementConstraints {

    /**
     * <p>getAttachingAnchor.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    Anchor getAttachingAnchor();
    /**
     * <p>getContentAnchor.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    Anchor getContentAnchor();
    /**
     * <p>getSizingPolicy.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.SizingPolicy} object
     */
    SizingPolicy getSizingPolicy();
    /**
     * <p>getBorderWidth.</p>
     *
     * @return a double
     */
    double getBorderWidth();
    /**
     * <p>getPadding.</p>
     *
     * @return a double
     */
    double getPadding();
    /**
     * <p>getAttachedTo.</p>
     *
     * @return a {@link gbw.melange.common.elementary.types.IElement} object
     */
    IElement<?> getAttachedTo();

}
