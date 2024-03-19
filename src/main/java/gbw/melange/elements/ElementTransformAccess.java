package gbw.melange.elements;

import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.types.IConstrainedElement;
import gbw.melange.common.elementary.types.IElement;

/**
 * Temporary adapter solution until MELANGE can be modularized for further visibility control </br>
 * TODO: REMOVE THIS
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementTransformAccess {
    /**
     * <p>setScale.</p>
     *
     * @param element a {@link gbw.melange.common.elementary.types.IConstrainedElement} object
     * @param x a double
     * @param y a double
     * @param z a double
     */
    public void setScale(IConstrainedElement element, double x, double y, double z){
        ((ComputedTransforms) element.computed()).getMatrix().setToScaling((float) x,(float) y,(float) z);
    }
    /**
     * <p>setTranslation.</p>
     *
     * @param element a {@link gbw.melange.common.elementary.types.IConstrainedElement} object
     * @param x a double
     * @param y a double
     * @param z a double
     */
    public void setTranslation(IConstrainedElement element, double x, double y, double z){
        ((ComputedTransforms) element.computed()).getMatrix().setTranslation((float) x, (float) y, (float) z);
    }

    public void setRotation(IConstrainedElement element, Vector3 axis, double deg){
        ((ComputedTransforms) element.computed()).getMatrix().setToRotation(axis,(float) deg);
    }
}
