package gbw.melange.common.elementary.types;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.contraints.IComputedTransforms;
import gbw.melange.common.elementary.contraints.IElementConstraints;

/**
 * Represents any Element with constraints.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IConstrainedElement extends Disposable {

    IComputedTransforms computed();
    IElementConstraints getConstraints();
    Mesh getMesh();

    /**
     * Resize the mesh to retain its proportions when the viewport resizes.
     * @param x0 Old viewport width
     * @param y0 Old viewport height
     * @param x1 New viewport width
     * @param y1 New viewport height
     */
    void onViewportResize(double x0, double y0, double x1, double y1);
}
