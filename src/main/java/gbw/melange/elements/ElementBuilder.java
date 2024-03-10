package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.MeshTable;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementConstraintBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.types.IPureElement;
import gbw.melange.common.elementary.types.IVolatileElement;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.elements.constraints.ElementConstraintBuilder;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.styling.ElementStyleBuilder;
import gbw.melange.elements.styling.ReferenceStyleDefinition;
import org.springframework.lang.NonNull;

/**
 * @param <T> the type of result from the given OnInit, if any.
 */
public class ElementBuilder<T> implements IElementBuilder<T> {

    //Allowed to be null:
    private IElement elementAttachedTo;
    private final ISpace parentSpace;
    private OnInit<T> onInit;

    //Not allowed to be null, but is null to detect changes.
    //There might be some about of unnecessary copying going on here, but before Rules are established its hard to know how careful to be in terms of deep copies / shallow copies
    private IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private IReferenceConstraintDefinition referenceConstraints = new ReferenceConstraintDefinition();

    private Mesh mesh = MeshTable.SQUARE.getMesh();
    private T content;

    public ElementBuilder(@NonNull ISpace parentSpace){
        this(parentSpace, (T) null);
    }
    public ElementBuilder(@NonNull ISpace parentSpace, T content){
        this.parentSpace = parentSpace;
        this.content = content;
    }
    public ElementBuilder(@NonNull ISpace parentSpace, OnInit<T> contentProvider){
        this.parentSpace = parentSpace;
        this.onInit = contentProvider;
    }

    @Override
    public IElement build() {
        if(onInit != null){
            IVolatileElement element = new OnInitElement<>(
                    mesh,
                    onInit,
                    elementAttachedTo,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addVolatileElement(element);
            return element;
        } else {
            IPureElement element = new PureElement(
                    mesh,
                    elementAttachedTo,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addVolatileElement(element);
            return element;
        }
    }

    @Override
    public IElementBuilder<T> stylingsFrom(IElement element) {
        this.referenceStyling = new ReferenceStyleDefinition(element.getStylings());
        return this;
    }

    @Override
    public IElementBuilder<T> stylingsFrom(IReferenceStyleDefinition refStyDef) {
        referenceStyling = refStyDef;
        return this;
    }

    @Override
    public IElementBuilder<T> constraintsFrom(IElement element) {
        this.referenceConstraints = new ReferenceConstraintDefinition(element.getConstraints());
        return this;
    }
    @Override
    public IElementBuilder<T> constraintsFrom(IReferenceConstraintDefinition refConDef) {
        this.referenceConstraints = refConDef;
        return this;
    }
    @Override
    public IElementBuilder<T> attachTo(IElement element) {
        this.elementAttachedTo = element;
        return this;
    }
    @Override
    public IElementBuilder<T> setMesh(Mesh mesh) {
        this.mesh = mesh.copy(true);
        return this;
    }
    @Override
    public IElementBuilder<T> onInit(OnInit<T> onInit) {
        this.onInit = onInit;
        return this;
    }

    @Override
    public IElementStyleBuilder<T> styling() {
        return new ElementStyleBuilder<>(this);
    }

    @Override
    public IElementConstraintBuilder<T> constraints() {
        return new ElementConstraintBuilder<>(this);
    }
}
