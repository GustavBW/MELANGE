package gbw.melange.common.elementary;

import gbw.melange.elements.constraints.SizingPolicy;

public interface IReferenceConstraintDefinition {
    Anchor attachingAnchor();
    void attachingAnchor(Anchor value);
    Anchor selfAnchor();
    void selfAnchor(Anchor value);
    SizingPolicy sizingPolicy();
    void sizingPolicy(SizingPolicy policy);
    double borderWidth();
    void borderWidth(double value);
    double padding();
    void padding(double value);
}
