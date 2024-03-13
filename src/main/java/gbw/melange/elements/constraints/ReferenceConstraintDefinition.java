package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.contraints.*;
import gbw.melange.common.elementary.types.IElement;

/**
 * <p>ReferenceConstraintDefinition class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ReferenceConstraintDefinition implements IReferenceConstraintDefinition {
    /** Constant <code>DEFAULT</code> */
    public static final ReferenceConstraintDefinition DEFAULT = new ReferenceConstraintDefinition();
    public Anchor attachingAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public Anchor contentAnchor = ElementAnchoring.TOP_LEFT.anchor;
    public SizingPolicy sizingPolicy = SizingPolicy.FILL_PARENT;
    public double borderWidth = 1;
    public double padding = 0;
    public IElement<?> elementAttachedTo;
    /**
     * <p>Constructor for ReferenceConstraintDefinition.</p>
     */
    public ReferenceConstraintDefinition(){}


    /**
     * <p>Constructor for ReferenceConstraintDefinition.</p>
     *
     * @param reference a {@link gbw.melange.common.elementary.contraints.IElementConstraints} object
     */
    public ReferenceConstraintDefinition(IElementConstraints reference){
        this.attachingAnchor = new Anchor(reference.getAttachingAnchor());
        this.contentAnchor = new Anchor(reference.getAttachingAnchor());
        this.sizingPolicy = reference.getSizingPolicy();
        this.borderWidth = reference.getBorderWidth();
        this.padding = reference.getPadding();
        this.elementAttachedTo = reference.getAttachedTo();
    }

    /** {@inheritDoc} */
    @Override
    public Anchor attachingAnchor() {
        return attachingAnchor;
    }

    /** {@inheritDoc} */
    @Override
    public void attachingAnchor(Anchor value) {
        this.attachingAnchor = value;
    }

    /** {@inheritDoc} */
    @Override
    public Anchor contentAnchor() {
        return contentAnchor;
    }

    /** {@inheritDoc} */
    @Override
    public void contentAnchor(Anchor value) {
        this.contentAnchor = value;
    }

    /** {@inheritDoc} */
    @Override
    public SizingPolicy sizingPolicy() {
        return sizingPolicy;
    }

    /** {@inheritDoc} */
    @Override
    public void sizingPolicy(SizingPolicy policy) {
        this.sizingPolicy = policy;
    }

    /** {@inheritDoc} */
    @Override
    public double borderWidth() {
        return borderWidth;
    }

    /** {@inheritDoc} */
    @Override
    public void borderWidth(double value) {
        this.borderWidth = value;
    }

    /** {@inheritDoc} */
    @Override
    public double padding() {
        return padding;
    }

    /** {@inheritDoc} */
    @Override
    public void padding(double value) {
        this.padding = value;
    }

    /** {@inheritDoc} */
    @Override
    public IElement<?> elementAttachedTo() {
        return elementAttachedTo;
    }

    /** {@inheritDoc} */
    @Override
    public void elementAttachedTo(IElement<?> value) {
        this.elementAttachedTo = value;
    }
}
