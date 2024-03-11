package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.types.IElement;

/**
 * Physical relations between a given Element and another.
 * A Child will always render on top of a parent
 */
public class ElementConstraints implements IElementConstraints {

    /**
     * Cascading <br/>
     * What part of this element anchors to the parents self anchor
     */
    private Anchor attachingAnchor = ElementAnchoring.TOP_LEFT.anchor;

    /**
     * Cascading <br/>
     * Where on this element, other elements attach
     */
    private Anchor selfAnchor = ElementAnchoring.TOP_LEFT.anchor;
    //Contained change
    private SizingPolicy sizingPolicy = SizingPolicy.FILL_PARENT;
    private IElement attachedTo;

    private double borderWidth = 1;
    private double padding = 1;

    public ElementConstraints(IReferenceConstraintDefinition def){
        if(def.selfAnchor() != null) this.selfAnchor = def.selfAnchor();
        if(def.attachingAnchor() != null) this.attachingAnchor = def.attachingAnchor();
        if(def.sizingPolicy() != null) this.sizingPolicy = def.sizingPolicy();
        this.borderWidth = def.borderWidth();
        this.padding = def.padding();
        this.attachedTo = def.elementAttachedTo();
    }

    @Override
    public Anchor getAttachingAnchor() {
        return attachingAnchor;
    }

    @Override
    public Anchor getSelfAnchor() {
        return selfAnchor;
    }

    @Override
    public SizingPolicy getSizingPolicy() {
        return sizingPolicy;
    }

    @Override
    public double getBorderWidth() {
        return borderWidth;
    }

    @Override
    public double getPadding() {
        return padding;
    }

    @Override
    public IElement getAttachedTo() {
        return attachedTo;
    }
}
