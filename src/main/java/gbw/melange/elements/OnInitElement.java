package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IInitElement;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;

public class OnInitElement<T> extends Element implements IInitElement {

    private final OnInit<T> onInit;

    OnInitElement(Mesh mesh, OnInit<T> onInit, IElement attachedTo, ReferenceStyleDefinition styling, ReferenceConstraintDefinition constraints) {
        super(mesh, attachedTo, styling, constraints);
        this.onInit = onInit;
    }
}
