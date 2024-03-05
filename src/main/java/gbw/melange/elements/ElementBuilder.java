package gbw.melange.elements;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.ISpace;
import gbw.melange.elements.styling.IElementStyleDefinition;

public class ElementBuilder implements IElementBuilder {
    public static IElementBuilder forSpace(ISpace space){
        return new ElementBuilder(space);
    }

    private final ISpace parentSpace;


    private ElementBuilder(ISpace parentSpace){
        this.parentSpace = parentSpace;
    }


    @Override
    public IElement build() {
        return null;
    }

    @Override
    public IElementBuilder stylingsFrom(IElement element) {
        return null;
    }

    @Override
    public IElementBuilder constraintsFrom(IElement element) {
        return null;
    }
}
