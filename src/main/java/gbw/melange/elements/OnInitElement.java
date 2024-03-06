package gbw.melange.elements;

import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IInitElement;
import gbw.melange.common.hooks.OnInit;

public class OnInitElement<T> extends Element implements IInitElement {

    private final OnInit<T> onInit;

    OnInitElement(OnInit<T> onInit, IElement attachedTo, IElementStyleDefinition styling, IElementConstraints constraints) {
        super(attachedTo, styling, constraints);
        this.onInit = onInit;
    }
}
