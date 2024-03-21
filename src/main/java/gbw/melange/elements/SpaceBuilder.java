package gbw.melange.elements;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.contraints.Anchor;
import gbw.melange.common.elementary.contraints.ElementAnchoring;
import gbw.melange.common.elementary.types.IConstrainedElement;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.types.ISpacerElement;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;

/**
 * <p>SpaceBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class SpaceBuilder implements ISpaceBuilder {

    private double width = 1;
    private double height = 1;
    private final ISpace parentSpace;
    private final ReferenceConstraintDefinition refConDef = new ReferenceConstraintDefinition();

    /**
     * <p>Constructor for SpaceBuilder.</p>
     *
     * @param space a {@link gbw.melange.common.elementary.space.ISpace} object
     */
    public SpaceBuilder(ISpace space){
        this.parentSpace = space;
    }

    /** {@inheritDoc} */
    @Override
    public IConstrainedElement build() {
        ISpacerElement spacer = new SpacerElement(refConDef, width, height);
        parentSpace.addSpace(spacer);
        return new SpacerElement(refConDef, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setWidth(double percentSPX) {
        this.width = percentSPX;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setHeight(double percentSPY) {
        this.height = percentSPY;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setSelfAnchor(ElementAnchoring anchor) {
        refConDef.contentAnchor = anchor.anchor;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setSelfAnchor(Anchor anchor) {
        refConDef.contentAnchor = anchor;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setAttachingAnchor(ElementAnchoring anchor) {
        refConDef.attachingAnchor = anchor.anchor;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder setAttachingAnchor(Anchor anchor) {
        refConDef.attachingAnchor = anchor;
        return this;
    }
}
