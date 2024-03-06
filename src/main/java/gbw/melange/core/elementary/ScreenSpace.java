package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.IInitElement;
import gbw.melange.common.elementary.IPureElement;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.elements.ElementBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ScreenSpace implements IScreenSpace {

    private final Matrix4 matrix = new Matrix4();
    private final IElementRenderer renderer = new ElementRenderer();
    private final List<IElement> renderQueue = new ArrayList<>();
    private final List<IElement> loadingQueue = new ArrayList<>();

    @Autowired
    public ScreenSpace(){

    }

    @Override
    public IElementBuilder createElement() {
        return new ElementBuilder(this);
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void addPureElement(IPureElement element) {
        renderQueue.add(element);
    }

    @Override
    public void addOnInitElement(IInitElement element) {
        loadingQueue.add(element);
    }


}
