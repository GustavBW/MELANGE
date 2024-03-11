package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.types.IPureElement;

/**
 * A "PureElement" is static for all intends and purposes. No special handling is required during boot for this element to work correctly.
 */
public class PureElement extends Element implements IPureElement {
    PureElement(Mesh mesh,IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints) {
        super(mesh, styling, constraints);
    }
}
