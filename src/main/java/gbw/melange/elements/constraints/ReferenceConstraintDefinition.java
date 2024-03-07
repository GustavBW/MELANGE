package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.IElementConstraints;

public class ReferenceConstraintDefinition {
    public static final ReferenceConstraintDefinition DEFAULT = new ReferenceConstraintDefinition();
    public Anchor attachingAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public Anchor selfAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public SizingPolicy sizingPolicy = SizingPolicy.FILL_PARENT;
    public double borderWidth = 1;
    public double padding = 1;
    public ReferenceConstraintDefinition(){}

    public ReferenceConstraintDefinition(IElementConstraints reference){
        this.attachingAnchor = new Anchor(reference.getAttachingAnchor());
        this.selfAnchor = new Anchor(reference.getAttachingAnchor());
        this.sizingPolicy = reference.getSizingPolicy();
        this.borderWidth = reference.getBorderWidth();
        this.padding = reference.getPadding();
    }
}
