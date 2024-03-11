package gbw.melange.common.elementary;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.types.IElement;

import java.util.Collection;

public interface IElementRenderer {

    void draw(Matrix4 parentMatrix, IElement<?>... elements);
    void draw(Matrix4 parentMatrix, Collection<IElement<?>> elements);

}
