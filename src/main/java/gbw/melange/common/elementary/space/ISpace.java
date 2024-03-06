package gbw.melange.common.elementary.space;

import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IInitElement;
import gbw.melange.common.elementary.IPureElement;
import gbw.melange.core.annotations.Space;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;

/**
 * A given ISpace may represent an area within which all assigned element's constraints must be resolved. <br/>
 * It is a way to limit cascading events, and to let elements be dynamically sized and positioned. <br/>
 */
@Space
public interface ISpace {

    /**
     * Create a new element belonging to this Space.
     */
    IElementBuilder<?> createElement();


    void render();

    void dispose();

    void addPureElement(IPureElement element);
    void addOnInitElement(IInitElement element);

}
