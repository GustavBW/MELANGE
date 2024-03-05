package gbw.melange.core.elementary;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.elements.ElementBuilder;

public class ScreenSpace implements IScreenSpace {

    public ScreenSpace(){}

    @Override
    public IElementBuilder createElement() {
        return new ElementBuilder(this);
    }

    @Override
    public void render(IElementRenderer renderer) {

    }

    @Override
    public void dispose() {

    }
}
