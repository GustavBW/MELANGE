package gbw.melange.common.builders;

import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.types.ISpacerElement;

public interface ISpaceBuilder extends IBuilder<ISpacerElement>{
    /**
     * @param percentSPX SPX = screen space x-axis / width.
     */
    ISpaceBuilder setWidth(double percentSPX);

    /**
     * @param percentSPY SPX = screen space y-axis / height.
     */
    ISpaceBuilder setHeight(double percentSPY);
    ISpaceBuilder setSelfAnchor(ElementAnchoring anchor);
    ISpaceBuilder setSelfAnchor(Anchor anchor);
    ISpaceBuilder setAttachingAnchor(ElementAnchoring anchor);
    ISpaceBuilder setAttachingAnchor(Anchor anchor);
}