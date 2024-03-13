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

    /**
     * <p>computed.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.IComputedTransforms} object
     */
    IComputedTransforms computed();
    /**
     * <p>getConstraints.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.IElementConstraints} object
     */
    IElementConstraints getConstraints();
    /**
     * <p>getMesh.</p>
     *
     * @return a {@link com.badlogic.gdx.graphics.Mesh} object
     */
    Mesh getMesh();
}
