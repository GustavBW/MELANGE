package gbw.melange.common.elementary.space;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.types.*;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.hooks.OnInit;

/**
 * A given ISpace may represent an area within which all assigned element's constraints must be resolved. <br/>
 * It is a way to limit cascading events, and to let elements be dynamically sized and positioned efficiently. <br/>
 */
public interface ISpace extends Disposable {

    void render();
    void resolveConstraints();
    void resolveConstraints(IConstrainedElement cascadeRoot);

    /**
     * Create a new element belonging to this Space.
     */
    IElementBuilder<?> createElement();
    /**
     * Create a new element belonging to this Space with the declared starting content.
     */
    <T> IElementBuilder<T> createElement(T content);
    /**
     * Create a new element belonging to this Space. It is not added and drawn immediately, but rather added to a separate loading queue, and a placeholder is drawn instead until the provider resolves.
     */
    <T> IElementBuilder<T> createElement(OnInit<T> contentProvider);

    /**
     * Creates a new {@link gbw.melange.common.elementary.types.ISpacerElement} that represents empty space.
     */
    ISpaceBuilder createSpace();
    /**
     * Manual addition method for {@link gbw.melange.common.elementary.types.ISpacerElement}
     */
    void addSpace(ISpacerElement spacer);

    /**
     * Manual version of {@link ISpace#createElement(T content)}
     */
    <T> void addPureElement(IPureElement<T> element);
    /**
     * Manual version of {@link ISpace#createElement(OnInit)}
     */
    <T> void addLoadingElement(ILoadingElement<T> element);

}
