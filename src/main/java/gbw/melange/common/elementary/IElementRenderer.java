package gbw.melange.common.elementary;

import com.badlogic.gdx.math.Matrix4;

import java.util.Collection;

public interface IElementRenderer {

    void draw(Matrix4 matrix, IElement... elements);
    void draw(Matrix4 matrix, Collection<IElement> elements);

}
