package gbw.melange.core.elementary;

import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementRenderer;

import java.util.Collection;

public class ElementRenderer implements IElementRenderer {
    private Matrix4 matrix4 = new Matrix4();

    @Override
    public void draw(IElement... elements) {
        for(IElement e : elements){
            drawElement(e);
        }
    }

    @Override
    public void draw(Collection<IElement> elements) {
        for(IElement e : elements){
            drawElement(e);
        }
    }

    private void drawElement(IElement elements){

    }
}
