package gbw.melange.common.elementary;

import gbw.melange.elements.constraints.SizingPolicy;

public interface IElementConstraints {

    Anchor getAttachingAnchor();
    Anchor getSelfAnchor();
    SizingPolicy getSizingPolicy();
    double getBorderWidth();
    double getPadding();

}
