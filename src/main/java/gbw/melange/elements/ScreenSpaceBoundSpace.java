package gbw.melange.elements;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.ISpace;

public class ScreenSpaceBoundSpace implements ISpace {


    @Override
    public IElementBuilder createElement() {
        return ElementBuilder.forSpace(this);
    }

    @Override
    public void render(IElementRenderer renderer) {

    }

    @Override
    public void dispose() {

    }
}
