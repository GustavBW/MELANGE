package gbw.melange.common.elementary.types;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.SizingPolicy;

/**
 * Represents any Element with constraints.
 */
public interface IConstrainedElement extends Disposable {

    IComputedTransforms computed();
    IElementConstraints getConstraints();
    Mesh getMesh();
}
