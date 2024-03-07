package gbw.melange.common.elementary.space;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IInitElement;
import gbw.melange.common.elementary.IPureElement;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;

/**
 * A given ISpace may represent an area within which all assigned element's constraints must be resolved. <br/>
 * It is a way to limit cascading events, and to let elements be dynamically sized and positioned. <br/>
 */
public interface ISpace extends Disposable {

    /**
     * Create a new element belonging to this Space.
     */
    IElementBuilder<?> createElement();

    void render();

    void addPureElement(IPureElement element);
    void addOnInitElement(IInitElement element);

}
