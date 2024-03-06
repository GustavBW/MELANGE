package gbw.melange.elements;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.elements.constraints.ElementConstraints;
import org.springframework.lang.NonNull;

/**
 * @param <T> the type of result from the given OnInit, if any.
 */
public class ElementBuilder<T> implements IElementBuilder<T> {

    private final ISpace parentSpace;
    private IElementStyleDefinition referenceStyling;
    private IElementConstraints referenceConstraints = new ElementConstraints();
    private IElement elementAttachedTo;
    private Anchor selfAnchor; //TODO: Generic building collections of properties for easy management. One thing JS had going for it
    private Anchor attachingAnchor;
    private OnInit<T> onInit;

    public ElementBuilder(@NonNull ISpace parentSpace){
        this.parentSpace = parentSpace;
    }

    @Override
    public IElement build() {
        if(onInit != null){
            IInitElement element = new OnInitElement(
                    onInit,
                    elementAttachedTo,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addOnInitElement(element);
            return element;
        } else {
            IPureElement element = new PureElement(
                    elementAttachedTo,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addPureElement(element);
            return element;
        }
    }

    @Override
    public IElementBuilder<T> stylingsFrom(IElement element) {
        this.referenceStyling = element.getStylings();
        return this;
    }

    @Override
    public IElementBuilder<T> constraintsFrom(IElement element) {
        this.referenceConstraints = element.getConstraints();
        return this;
    }

    @Override
    public IElementBuilder<T> setSelfAnchor(ElementAnchoring anchor) {
        this.selfAnchor = anchor.anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setSelfAnchor(Anchor anchor) {
        this.selfAnchor = anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setAttachingAnchor(ElementAnchoring anchor) {
        this.attachingAnchor = anchor.anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> setAttachingAnchor(Anchor anchor) {
        this.attachingAnchor = anchor;
        return this;
    }

    @Override
    public IElementBuilder<T> attachTo(IElement element) {
        this.elementAttachedTo = element;
        return this;
    }

    @Override
    public IElementBuilder<T> onInit(OnInit<T> onInit) {
        this.onInit = onInit;
        return this;
    }
}
