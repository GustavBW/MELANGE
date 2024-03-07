package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
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
import java.util.stream.Stream;

public class ScreenSpace implements IScreenSpace {

    private final Matrix4 matrix = new Matrix4();
    private final IElementRenderer renderer = new ElementRenderer();
    private final List<IElement> renderQueue = new ArrayList<>();
    private final List<IElement> loadingQueue = new ArrayList<>();
    private final List<IElement> errorQueue = new ArrayList<>();

    @Override
    public IElementBuilder<?> createElement() {
        return new ElementBuilder<>(this);
    }

    @Override
    public void render() {
        //Draw stuff
        renderer.draw(matrix, renderQueue);
        //Evaluate OnInits
    }

    @Override
    public void dispose() {
        Stream.of(renderQueue, loadingQueue, errorQueue)
                .flatMap(List::stream)
                .forEach(Disposable::dispose);
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
