package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IPureElement;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;

/**
 * A "PureElement" is static for all intends and purposes. No special handling is required during boot for this element to work correctly.
 */
public class PureElement extends Element implements IPureElement {
    PureElement(Mesh mesh, IElement attachedTo, ReferenceStyleDefinition styling, ReferenceConstraintDefinition constraints) {
        super(mesh, attachedTo, styling, constraints);
    }
}
