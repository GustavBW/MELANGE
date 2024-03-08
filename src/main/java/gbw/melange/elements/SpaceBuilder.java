package gbw.melange.elements;

import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.types.ISpacerElement;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;

public class SpaceBuilder implements ISpaceBuilder {

    private double width = 1;
    private double height = 1;
    private final ISpace parentSpace;
    private final ReferenceConstraintDefinition refConDef = new ReferenceConstraintDefinition();

    public SpaceBuilder(ISpace space){
        this.parentSpace = space;
    }

    @Override
    public ISpacerElement build() {
        ISpacerElement spacer = new SpacerElement(refConDef, width, height);
        parentSpace.addSpace(spacer);
        return new SpacerElement(refConDef, width, height);
    }

    @Override
    public ISpaceBuilder setWidth(double percentSPX) {
        this.width = percentSPX;
        return this;
    }

    @Override
    public ISpaceBuilder setHeight(double percentSPY) {
        this.height = percentSPY;
        return this;
    }

    @Override
    public ISpaceBuilder setSelfAnchor(ElementAnchoring anchor) {
        refConDef.selfAnchor = anchor.anchor;
        return this;
    }

    @Override
    public ISpaceBuilder setSelfAnchor(Anchor anchor) {
        refConDef.selfAnchor = anchor;
        return this;
    }

    @Override
    public ISpaceBuilder setAttachingAnchor(ElementAnchoring anchor) {
        refConDef.attachingAnchor = anchor.anchor;
        return this;
    }

    @Override
    public ISpaceBuilder setAttachingAnchor(Anchor anchor) {
        refConDef.attachingAnchor = anchor;
        return this;
    }
}
