package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.events.observability.ObservableValue;

/**
 * <p>LoadingElement class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class LoadingElement<T> extends Element<T> implements ILoadingElement<T> {

    private final IContentProvider<T> provider;

    private final IFallibleBlockingObservable<T> content = ObservableValue.blockingFallible(null);

    LoadingElement(Mesh mesh, IContentProvider<T> provider, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
        this.provider = provider;
        super.setState(ElementState.LOADING);
    }

    /** {@inheritDoc} */
    @Override
    public T getContent() {
        return content.get();
    }

    /** {@inheritDoc} */
    @Override
    public void setContent(T content) throws Exception {
        this.content.set(content);
    }

    /** {@inheritDoc} */
    @Override
    public void invokeProvider() throws Exception {
        final T result;
        try{
            result = provider.fetch();
            super.setState(ElementState.STABLE);
        }catch (Exception propagated){
            super.setState(ElementState.ERROR);
            throw propagated;
        }
        setContent(result);
    }
}
