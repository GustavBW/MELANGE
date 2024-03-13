package gbw.melange.common.elementary.types;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.contraints.IComputedTransforms;
import gbw.melange.common.elementary.contraints.IElementConstraints;

/**
 * Represents any Element with constraints.
 */
public interface IConstrainedElement extends Disposable {

    IComputedTransforms computed();
    IElementConstraints getConstraints();
    Mesh getMesh();
}
