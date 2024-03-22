package gbw.melange.elements;

import gbw.melange.mesh.ManagedMesh;
import gbw.melange.mesh.IManagedMesh;
import gbw.melange.mesh.constants.MeshTable;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementConstraintBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.types.IPureElement;
import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.elements.constraints.ElementConstraintBuilder;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.styling.ElementStyleBuilder;
import gbw.melange.elements.styling.ReferenceStyleDefinition;
import org.springframework.lang.NonNull;

/**
 * <p>ElementBuilder class.</p>
 *
 * @param <T> the type of result from the given OnInit, if any.
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementBuilder<T> implements IElementBuilder<T> {

    //Not allowed to be null, but is null to detect changes.
    //There might be some about of unnecessary copying going on here, but before Rules are established it's hard to know how careful to be in terms of deep copies / shallow copies
    private IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private IReferenceConstraintDefinition referenceConstraints = new ReferenceConstraintDefinition();

    private IManagedMesh baseMesh = new ManagedMesh(MeshTable.SQUARE.getMesh());
    private T content;
    private final ISpace parentSpace;
    private IContentProvider<T> contentProvider;

    //Sub builders
    private final IElementStyleBuilder<T> styleBuilder = new ElementStyleBuilder<>(this);
    private final IElementConstraintBuilder<T> constraintBuilder = new ElementConstraintBuilder<>(this);

    public ElementBuilder(@NonNull ISpace parentSpace){
        this.parentSpace = parentSpace;
    }
    public ElementBuilder(@NonNull ISpace parentSpace, IContentProvider<T> contentProvider){
        this.parentSpace = parentSpace;
        this.contentProvider = contentProvider;
    }

    /** {@inheritDoc} */
    @Override
    public IElement<T> build() {
        //TODO: Based on <T>, find the matching content renderer

        if(contentProvider != null){
            ILoadingElement<T> element = new LoadingElement<>(
                    baseMesh,
                    contentProvider,
                    content,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addLoadingElement(element);
            return element;
        } else {
            IPureElement<T> element = new PureElement<>(
                    baseMesh,
                    content,
                    referenceStyling,
                    referenceConstraints
            );
            parentSpace.addPureElement(element);
            return element;
        }
    }

    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> setShape(IManagedMesh mesh) {
        this.baseMesh = mesh;
        return this;
    }
    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> setContent(IContentProvider<T> provider) {
        this.contentProvider = provider;
        return this;
    }
    @Override
    public IElementBuilder<T> setContent(T content){
        this.content = content;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> stylingsFrom(IElement<?> element) {
        this.referenceStyling = new ReferenceStyleDefinition(element.getStylings());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> stylingsFrom(IReferenceStyleDefinition refStyDef) {
        referenceStyling = refStyDef;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> constraintsFrom(IElement<?> element) {
        this.referenceConstraints = new ReferenceConstraintDefinition(element.getConstraints());
        return this;
    }
    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> constraintsFrom(IReferenceConstraintDefinition refConDef) {
        this.referenceConstraints = refConDef;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> styling() {
        return styleBuilder;
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraintBuilder<T> constraints() {
        return constraintBuilder;
    }
}
