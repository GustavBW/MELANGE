package gbw.melange.common.elementary;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.types.IElement;

import java.util.Collection;

/**
 * <p>IElementRenderer interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementRenderer {

    /**
     * <p>draw.</p>
     *
     * @param parentMatrix a {@link com.badlogic.gdx.math.Matrix4} object
     * @param elements a {@link gbw.melange.common.elementary.types.IElement} object
     */
    void draw(Matrix4 parentMatrix, IElement<?>... elements);
    /**
     * <p>draw.</p>
     *
     * @param parentMatrix a {@link com.badlogic.gdx.math.Matrix4} object
     * @param elements a {@link java.util.Collection} object
     */
    void draw(Matrix4 parentMatrix, Collection<IElement<?>> elements);

}
