package gbw.melange.elements;

import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IPureElement;

public class PureElement extends Element implements IPureElement {
    PureElement(IElement attachedTo, IElementStyleDefinition styling, IElementConstraints constraints) {
        super(attachedTo, styling, constraints);
    }
}
