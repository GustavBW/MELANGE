package gbw.melange.elements.constraints;

import gbw.melange.common.builders.IBuilder;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementConstraintBuilder;
import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.types.IElement;

public class ElementConstraintBuilder<T> implements IElementConstraintBuilder<T> {

    private final IElementBuilder<T> parentBuilder;

    private IReferenceConstraintDefinition referenceConstraints = new ReferenceConstraintDefinition();
    public ElementConstraintBuilder(IElementBuilder<T> parentBuilder){
        this.parentBuilder = parentBuilder;
    }

    @Override
    public IElementBuilder<T> apply() {
        parentBuilder.constraintsFrom(referenceConstraints);
        return parentBuilder;
    }
    @Override
    public IElementConstraintBuilder<T> attachTo(IElement element) {
        referenceConstraints.elementAttachedTo(element);
        return this;
    }
    @Override
    public IElementConstraintBuilder<T> setSelfAnchor(ElementAnchoring anchor) {
        return setSelfAnchor(anchor.anchor);
    }

    @Override
    public IElementConstraintBuilder<T> setSelfAnchor(Anchor anchor) {
        referenceConstraints.selfAnchor(anchor);
        return this;
    }

    @Override
    public IElementConstraintBuilder<T> setAttachingAnchor(ElementAnchoring anchor) {
        return setAttachingAnchor(anchor.anchor);
    }

    @Override
    public IElementConstraintBuilder<T> setAttachingAnchor(Anchor anchor) {
        referenceConstraints.attachingAnchor(anchor);
        return this;
    }

    @Override
    public IElementConstraintBuilder<T> setPadding(double percent) {
        referenceConstraints.padding(percent);
        return this;
    }

    @Override
    public IElementConstraintBuilder<T> setBorderWidth(double percent) {
        referenceConstraints.borderWidth(percent);
        return this;
    }
}
