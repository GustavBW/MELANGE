package gbw.melange.common.elementary.space;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.builders.ISpaceBuilder;
import gbw.melange.common.elementary.IContentProvider;
import gbw.melange.common.elementary.types.*;
import gbw.melange.common.builders.IElementBuilder;

/**
 * A given ISpace may represent an area within which all assigned element's constraints must be resolved. <br/>
 * It is a way to limit cascading events, and to let elements be dynamically sized and positioned efficiently. <br/>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ISpace extends Disposable {

    /**
     * <p>render.</p>
     */
    void render();
    /**
     * <p>render.</p>
     *
     * @param testCamMatrix a {@link com.badlogic.gdx.math.Matrix4} object
     */
    void render(Matrix4 testCamMatrix);
    /**
     * <p>resolveConstraints.</p>
     */
    void resolveConstraints();
    /**
     * <p>resolveConstraints.</p>
     *
     * @param cascadeRoot a {@link gbw.melange.common.elementary.types.IConstrainedElement} object
     */
    void resolveConstraints(IConstrainedElement cascadeRoot);

    /**
     * Create a new element belonging to this Space.
     *
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<?> createElement();
    /**
     * Create a new element belonging to this Space with the declared starting content.
     *
     * @param content a T object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    <T> IElementBuilder<T> createElement(T content);
    /**
     * Create a new element belonging to this Space. It is not added and drawn immediately, but rather added to a separate loading queue, and a placeholder is drawn instead until the provider resolves.
     *
     * @param contentProvider a {@link gbw.melange.common.elementary.IContentProvider} object
     * @param <T> a T class
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    <T> IElementBuilder<T> createElement(IContentProvider<T> contentProvider);

    /**
     * Creates a new {@link gbw.melange.common.elementary.types.ISpacerElement} that represents empty space.
     *
     * @return a {@link gbw.melange.common.builders.ISpaceBuilder} object
     */
    ISpaceBuilder createSpace();
    /**
     * Manual addition method for {@link gbw.melange.common.elementary.types.ISpacerElement}
     *
     * @param spacer a {@link gbw.melange.common.elementary.types.ISpacerElement} object
     */
    void addSpace(ISpacerElement spacer);

    /**
     * Manual version of {@link gbw.melange.common.elementary.space.ISpace#createElement(T content)}
     *
     * @param element a {@link gbw.melange.common.elementary.types.IPureElement} object
     * @param <T> a T class
     */
    <T> void addPureElement(IPureElement<T> element);
    /**
     * Manual version of {@link gbw.melange.common.elementary.space.ISpace#createElement(IContentProvider)}
     *
     * @param element a {@link gbw.melange.common.elementary.types.ILoadingElement} object
     * @param <T> a T class
     */
    <T> void addLoadingElement(ILoadingElement<T> element);

}
