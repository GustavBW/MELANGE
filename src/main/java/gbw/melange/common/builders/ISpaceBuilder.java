package gbw.melange.common.builders;

import gbw.melange.common.elementary.contraints.Anchor;
import gbw.melange.common.elementary.contraints.ElementAnchoring;
import gbw.melange.common.elementary.types.IConstrainedElement;

/**
 * <p>ISpaceBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ISpaceBuilder extends IBuilder<IConstrainedElement>{
    /**
     * <p>setWidth.</p>
     *
     * @param percentSPX SPX = screen space x-axis / width.
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setWidth(double percentSPX);

    /**
     * <p>setHeight.</p>
     *
     * @param percentSPY SPX = screen space y-axis / height.
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setHeight(double percentSPY);
    /**
     * <p>setSelfAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.ElementAnchoring} object
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setSelfAnchor(ElementAnchoring anchor);
    /**
     * <p>setSelfAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.Anchor} object
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setSelfAnchor(Anchor anchor);
    /**
     * <p>setAttachingAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.ElementAnchoring} object
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setAttachingAnchor(ElementAnchoring anchor);
    /**
     * <p>setAttachingAnchor.</p>
     *
     * @param anchor a {@link gbw.melange.common.elementary.contraints.Anchor} object
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder setAttachingAnchor(Anchor anchor);
}
