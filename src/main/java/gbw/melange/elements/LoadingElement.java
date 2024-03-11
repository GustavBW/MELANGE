package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.events.observability.ObservableValue;

public class LoadingElement<T> extends Element<T> implements ILoadingElement<T> {

    private final OnInit<T> onInit;

    private final IFallibleBlockingObservable<T> content = ObservableValue.blockingFallible(null);

    LoadingElement(Mesh mesh, OnInit<T> onInit, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
        this.onInit = onInit;
        super.setState(ElementState.LOADING);
    }

    @Override
    public T getContent() {
        return content.get();
    }

    @Override
    public void setContent(T content) throws Exception {
        this.content.set(content);
    }

    @Override
    public T onInit() throws Exception {
        final T result;
        try{
            result = onInit.onInit();
            super.setState(ElementState.STABLE);
        }catch (Exception propagated){
            super.setState(ElementState.ERROR);
            throw propagated;
        }
        setContent(result);
        return result;
    }
}