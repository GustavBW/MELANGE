package gbw.melange.common.elementary.contraints;

import gbw.melange.common.elementary.types.IElement;

public interface IReferenceConstraintDefinition {
    Anchor attachingAnchor();
    void attachingAnchor(Anchor value);
    Anchor contentAnchor();
    void contentAnchor(Anchor value);
    SizingPolicy sizingPolicy();
    void sizingPolicy(SizingPolicy policy);
    double borderWidth();
    void borderWidth(double value);
    double padding();
    void padding(double value);
    IElement<?> elementAttachedTo();
    void elementAttachedTo(IElement<?> value);
}
