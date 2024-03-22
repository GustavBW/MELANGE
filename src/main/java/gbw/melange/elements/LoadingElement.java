package gbw.melange.elements;

import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.elements.problematic.Element;
import gbw.melange.events.observability.ObservableValue;
import gbw.melange.mesh.IManagedMesh;

/**
 * <p>LoadingElement class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class LoadingElement<T> extends Element<T> implements ILoadingElement<T> {

    private final IContentProvider<T> provider;

    private final IFallibleBlockingObservable<T> content;

    LoadingElement(IManagedMesh mesh, IContentProvider<T> provider, T defaultValue, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
        this.provider = provider;
        this.content = ObservableValue.blockingFallible(defaultValue);
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
