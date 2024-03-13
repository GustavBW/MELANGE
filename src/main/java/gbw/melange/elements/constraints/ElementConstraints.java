package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.contraints.*;
import gbw.melange.common.elementary.types.IElement;

/**
 * Physical relations between a given Element and another.
 * A Child will always render on top of a parent
 *
 * @author GustavBW
 * @version $Id: $Id
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
    private Anchor contentAnchor = ElementAnchoring.TOP_LEFT.anchor;
    //Contained change
    private SizingPolicy sizingPolicy = SizingPolicy.FILL_PARENT;
    private IElement attachedTo;

    private double borderWidth = 1;
    private double padding = 0;

    /**
     * <p>Constructor for ElementConstraints.</p>
     *
     * @param def a {@link gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition} object
     */
    public ElementConstraints(IReferenceConstraintDefinition def){
        if(def.contentAnchor() != null) this.contentAnchor = def.contentAnchor();
        if(def.attachingAnchor() != null) this.attachingAnchor = def.attachingAnchor();
        if(def.sizingPolicy() != null) this.sizingPolicy = def.sizingPolicy();
        this.borderWidth = def.borderWidth();
        this.padding = def.padding();
        this.attachedTo = def.elementAttachedTo();
    }

    /** {@inheritDoc} */
    @Override
    public Anchor getAttachingAnchor() {
        return attachingAnchor;
    }

    /** {@inheritDoc} */
    @Override
    public Anchor getContentAnchor() {
        return contentAnchor;
    }

    /** {@inheritDoc} */
    @Override
    public SizingPolicy getSizingPolicy() {
        return sizingPolicy;
    }

    /** {@inheritDoc} */
    @Override
    public double getBorderWidth() {
        return borderWidth;
    }

    /** {@inheritDoc} */
    @Override
    public double getPadding() {
        return padding;
    }

    /** {@inheritDoc} */
    @Override
    public IElement getAttachedTo() {
        return attachedTo;
    }
}
