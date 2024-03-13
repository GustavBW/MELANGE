package gbw.melange.common.builders;

import gbw.melange.common.elementary.contraints.Anchor;
import gbw.melange.common.elementary.contraints.ElementAnchoring;
import gbw.melange.common.elementary.types.IElement;

/**
 * <p>IElementConstraintBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementConstraintBuilder<T> extends IPartialBuilder<IElementBuilder<T>>{
    /**
     * <p>setContentAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.ElementAnchoring} object
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setContentAnchor(ElementAnchoring anchor);
    /**
     * <p>setContentAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.Anchor} object
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setContentAnchor(Anchor anchor);
    /**
     * <p>setAttachingAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.ElementAnchoring} object
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setAttachingAnchor(ElementAnchoring anchor);
    /**
     * <p>setAttachingAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.Anchor} object
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setAttachingAnchor(Anchor anchor);
    /**
     * <p>setPadding.</p>
     *
     * @param percent a double
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setPadding(double percent);
    /**
     * <p>setBorderWidth.</p>
     *
     * @param percent a double
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> setBorderWidth(double percent);
    /**
     * <p>attachTo.</p>
     *
     * @param element a {@link gbw.melange.common.elementary.types.IElement} object
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> attachTo(IElement<?> element);
}
