package gbw.melange.elements.constraints;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementConstraintBuilder;
import gbw.melange.common.elementary.contraints.Anchor;
import gbw.melange.common.elementary.contraints.ElementAnchoring;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.types.IElement;

/**
 * <p>ElementConstraintBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementConstraintBuilder<T> implements IElementConstraintBuilder<T> {

    private final IElementBuilder<T> parentBuilder;

    private IReferenceConstraintDefinition referenceConstraints = new ReferenceConstraintDefinition();
    /**
     * <p>Constructor for ElementConstraintBuilder.</p>
     *
     * @param parentBuilder a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    public ElementConstraintBuilder(IElementBuilder<T> parentBuilder){
        this.parentBuilder = parentBuilder;
    }

    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> apply() {
        parentBuilder.constraintsFrom(referenceConstraints);
        return parentBuilder;
    }
    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> attachTo(IElement element) {
        referenceConstraints.elementAttachedTo(element);
        return this;
    }
    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setContentAnchor(ElementAnchoring anchor) {
        return setContentAnchor(anchor.anchor);
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setContentAnchor(Anchor anchor) {
        referenceConstraints.contentAnchor(anchor);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setAttachingAnchor(ElementAnchoring anchor) {
        return setAttachingAnchor(anchor.anchor);
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setAttachingAnchor(Anchor anchor) {
        referenceConstraints.attachingAnchor(anchor);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setPadding(double percent) {
        referenceConstraints.padding(percent);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> setBorderWidth(double percent) {
        referenceConstraints.borderWidth(percent);
        return this;
    }
}
