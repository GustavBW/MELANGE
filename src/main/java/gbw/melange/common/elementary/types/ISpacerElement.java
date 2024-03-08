package gbw.melange.common.elementary.types;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.IElementConstraints;

/**
 * Represents empty space. If a nearby element has {@link gbw.melange.elements.constraints.SizingPolicy#FIT_CONTENT}, it will take priority in terms of space use.
 */
public interface ISpacerElement extends Disposable {
    double getRequestedHeight();
    double getRequestedWidth();
    IElementConstraints getConstraints();
    Mesh getMesh();
}
