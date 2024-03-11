package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IVolatileElement;
import gbw.melange.common.events.observability.EqualityFunction;
import gbw.melange.common.events.observability.IFallibleBlockingObservable;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.events.observability.ObservableValue;

public class VolatileElement<T> extends Element<T> implements IVolatileElement<T> {

    private final OnInit<T> onInit;

    private final IFallibleBlockingObservable<T> content = ObservableValue.blockingFallible(null);

    VolatileElement(Mesh mesh, OnInit<T> onInit, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
        this.onInit = onInit;
    }

    @Override
    public T getContent() {
        return content.get();
    }
    @Override
    public void setContent(T content) throws Exception {
        this.content.set(content);
    }

}
