package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.types.*;
import gbw.melange.core.ParallelMonitoredExecutionEnvironment;
import gbw.melange.elements.ElementBuilder;
import gbw.melange.elements.problematic.ElementRenderer;
import gbw.melange.elements.SpaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>ScreenSpace class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ScreenSpace implements IScreenSpace {

    private static final Logger log = LogManager.getLogger();
    private final Matrix4 matrix = new Matrix4();
    private final IElementRenderer renderer = new ElementRenderer();
    private final IAETR constraintResolver = new AutomaticElementTransformResolver();
    private final List<IElement<?>> renderQueue = new CopyOnWriteArrayList<>();
    private final List<IElement<?>> loadingQueue = new CopyOnWriteArrayList<>();
    private final List<IElement<?>> errorQueue = new CopyOnWriteArrayList<>();
    private final List<IConstrainedElement> additionOrder = new ArrayList<>();
    private boolean visible = false;
    private final IMelangeConfig config;

    ScreenSpace(IMelangeConfig config){
        this.config = config;
    }

    /** {@inheritDoc} */
    @Override
    public void render(Matrix4 parentMatrix) {
        renderer.draw(parentMatrix, renderQueue);
    }

    @Override
    public void show() {
        //TODO: Add viewport size and scale check
        this.visible = true;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        //Draw stuff
        renderer.draw(matrix, renderQueue);
        //Evaluate OnInits
    }

    /** {@inheritDoc} */
    @Override
    public void resolveConstraints() {
        constraintResolver.load(additionOrder);
        constraintResolver.resolve();
    }

    /** {@inheritDoc} */
    @Override
    public void resolveConstraints(IConstrainedElement cascadeRoot) {
        constraintResolver.resolveFrom(cascadeRoot);
    }

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        additionOrder.forEach(Disposable::dispose);
    }
    /** {@inheritDoc} */
    @Override
    public IElementBuilder<?> createElement() {
        return new ElementBuilder<>(this);
    }
    /** {@inheritDoc} */
    @Override
    public ISpaceBuilder createSpace() {
        return new SpaceBuilder(this);
    }
    /** {@inheritDoc} */
    @Override
    public void addSpace(ISpacerElement spacer) {
        additionOrder.add(spacer);
    }
    /** {@inheritDoc} */
    @Override
    public <T> void addPureElement(IPureElement<T> element) {
        additionOrder.add(element);
        renderQueue.add(element);
    }
    /** {@inheritDoc} */
    @Override
    public <T> void addLoadingElement(ILoadingElement<T> element) {
        additionOrder.add(element);
        loadingQueue.add(element);
        ParallelMonitoredExecutionEnvironment.offloadLoadingElement(
                element,
                () -> moveFromLoadingToRender(element),
                () -> moveFromLoadingToError(element)
        );
    }
    private void moveFromLoadingToRender(IElement<?> element){
        loadingQueue.remove(element);
        renderQueue.add(element);
        resolveConstraints(element);

        if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.ELEMENT_UPDATES)){
            log.info(element + " moved to render queue");
        }
    }
    private void moveFromLoadingToError(IElement<?> element){
        loadingQueue.remove(element);
        errorQueue.add(element);
        resolveConstraints(element);

        if(config.getLoggingAspects().contains(IMelangeConfig.LogLevel.ELEMENT_UPDATES)) {
            log.info(element + " moved to error queue");
        }
    }
}
