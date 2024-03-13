package gbw.melange.common.elementary.contraints;

import gbw.melange.common.elementary.types.IElement;


public interface IElementConstraints {

    Anchor getAttachingAnchor();
    Anchor getContentAnchor();
    SizingPolicy getSizingPolicy();
    double getBorderWidth();
    double getPadding();
    IElement<?> getAttachedTo();

}
