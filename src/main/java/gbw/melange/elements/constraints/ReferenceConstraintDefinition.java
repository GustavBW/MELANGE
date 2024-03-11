package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.types.IElement;

public class ReferenceConstraintDefinition implements IReferenceConstraintDefinition {
    public static final ReferenceConstraintDefinition DEFAULT = new ReferenceConstraintDefinition();
    public Anchor attachingAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public Anchor selfAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public SizingPolicy sizingPolicy = SizingPolicy.FILL_PARENT;
    public double borderWidth = 1;
    public double padding = 1;
    public IElement elementAttachedTo;
    public ReferenceConstraintDefinition(){}


    public ReferenceConstraintDefinition(IElementConstraints reference){
        this.attachingAnchor = new Anchor(reference.getAttachingAnchor());
        this.selfAnchor = new Anchor(reference.getAttachingAnchor());
        this.sizingPolicy = reference.getSizingPolicy();
        this.borderWidth = reference.getBorderWidth();
        this.padding = reference.getPadding();
        this.elementAttachedTo = reference.getAttachedTo();
    }

    @Override
    public Anchor attachingAnchor() {
        return attachingAnchor;
    }

    @Override
    public void attachingAnchor(Anchor value) {
        this.attachingAnchor = value;
    }

    @Override
    public Anchor selfAnchor() {
        return selfAnchor;
    }

    @Override
    public void selfAnchor(Anchor value) {
        this.selfAnchor = value;
    }

    @Override
    public SizingPolicy sizingPolicy() {
        return sizingPolicy;
    }

    @Override
    public void sizingPolicy(SizingPolicy policy) {
        this.sizingPolicy = policy;
    }

    @Override
    public double borderWidth() {
        return borderWidth;
    }

    @Override
    public void borderWidth(double value) {
        this.borderWidth = value;
    }

    @Override
    public double padding() {
        return padding;
    }

    @Override
    public void padding(double value) {
        this.padding = value;
    }

    @Override
    public IElement elementAttachedTo() {
        return elementAttachedTo;
    }

    @Override
    public void elementAttachedTo(IElement value) {
        this.elementAttachedTo = value;
    }
}
