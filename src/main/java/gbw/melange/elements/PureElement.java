package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IPureElement;
import gbw.melange.common.events.observability.IPrestineBlockingObservable;
import gbw.melange.events.observability.ObservableValue;

/**
 * A "PureElement" is static for all intends and purposes. No special handling is required during boot for this element to work correctly.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class PureElement<T> extends Element<T> implements IPureElement<T> {

    private final IPrestineBlockingObservable<T> content;

    PureElement(Mesh mesh,IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        this(null, mesh, styling, constraints);
    }
    PureElement(T content, Mesh mesh,IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
        this.content = ObservableValue.blockingPristine(content);
        super.setState(ElementState.STABLE);
    }

    /** {@inheritDoc} */
    @Override
    public T getContent(){
        return content.get();
    }
    /** {@inheritDoc} */
    @Override
    public void setContent(T content){
        this.content.set(content);
    }
}
