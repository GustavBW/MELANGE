package gbw.melange.elements;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.space.ISpace;

public class ElementBuilder implements IElementBuilder {

    private final ISpace parentSpace;


    public ElementBuilder(ISpace parentSpace){
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
