package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.types.IVolatileElement;
import gbw.melange.common.hooks.OnInit;

public class OnInitElement<T> extends Element implements IVolatileElement {

    private final OnInit<T> onInit;

    OnInitElement(Mesh mesh, OnInit<T> onInit, IElement attachedTo, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, attachedTo, styling, constraints);
        this.onInit = onInit;
    }
}
