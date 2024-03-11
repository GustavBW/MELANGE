package gbw.melange.elements;

import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.types.IElement;

/**
 * Temporary adapter solution until MELANGE can be modularized for further visibility control </br>
 * TODO: REMOVE THIS
 */
public class ElementTransformAccess {

    public void setScale(IElement<?> element, double x, double y, double z){
        ((ComputedTransforms) element.computed()).getMatrix().setToScaling((float) x,(float) y,(float) z);
    }

    public void setTranslation(IElement<?> element, double x, double y, double z){
        ((ComputedTransforms) element.computed()).getMatrix().setTranslation((float) x, (float) y, (float) z);
    }



}
