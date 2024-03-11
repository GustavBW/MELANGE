package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.types.IPureElement;
import gbw.melange.common.elementary.types.ISpacerElement;
import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.core.ParallelMonitoredExecutionEnvironment;
import gbw.melange.elements.ElementBuilder;
import gbw.melange.elements.ElementRenderer;
import gbw.melange.elements.SpaceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ScreenSpace implements IScreenSpace {

    private static final Logger log = LoggerFactory.getLogger(ScreenSpace.class);
    private final Matrix4 matrix = new Matrix4();
    private final IElementRenderer renderer = new ElementRenderer();
    private final List<ISpacerElement> spacers = new ArrayList<>();
    private final List<IElement<?>> renderQueue = new CopyOnWriteArrayList<>();
    private final List<IElement<?>> loadingQueue = new CopyOnWriteArrayList<>();
    private final List<IElement<?>> errorQueue = new CopyOnWriteArrayList<>();

    @Override
    public IElementBuilder<?> createElement() {
        return createElement(null);
    }

    @Override
    public <T> IElementBuilder<T> createElement(T content) {
        return new ElementBuilder<>(this, content);
    }

    @Override
    public <T> IElementBuilder<T> createElement(OnInit<T> contentProvider) {
        return new ElementBuilder<>(this, contentProvider);
    }

    @Override
    public ISpaceBuilder createSpace() {
        return new SpaceBuilder(this);
    }

    @Override
    public void addSpace(ISpacerElement spacer) {
        spacers.add(spacer);
    }

    @Override
    public void render() {
        //Draw stuff
        renderer.draw(matrix, renderQueue);
        //Evaluate OnInits
    }

    @Override
    public void dispose() {
        Stream.of(renderQueue, loadingQueue, errorQueue, spacers)
                .flatMap(List::stream)
                .forEach(Disposable::dispose);

    }

    @Override
    public <T> void addPureElement(IPureElement<T> element) {
        renderQueue.add(element);
    }

    @Override
    public <T> void addLoadingElement(ILoadingElement<T> element) {
        loadingQueue.add(element);
        ParallelMonitoredExecutionEnvironment.offloadVolatileElement(
                element,
                () -> moveFromLoadingToRender(element),
                () -> moveFromLoadingToError(element)
        );
    }
    private void moveFromLoadingToRender(IElement<?> element){
        loadingQueue.remove(element);
        renderQueue.add(element);
        log.info(element + " moved to render queue");
    }
    private void moveFromLoadingToError(IElement<?> element){
        loadingQueue.remove(element);
        errorQueue.add(element);
        log.info(element + " moved to error queue");
    }

    private <T> boolean dostShadersBeCompiled(IElement<T> element){
        return true; //TODO: Shader handling has been moved to wrapper instantiation.
    }


}
