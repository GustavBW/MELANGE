package gbw.melange.core.elementary;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.MeshTable;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementRenderer;

import java.util.Collection;

public class ElementRenderer implements IElementRenderer {

    public ElementRenderer(){
    }

    @Override
    public void draw(Matrix4 matrix, IElement... elements) {
        for(IElement e : elements){
            drawElement(matrix, e);
        }
    }

    @Override
    public void draw(Matrix4 matrix, Collection<IElement> elements) {
        for(IElement e : elements){
            drawElement(matrix, e);
        }
    }

    private void drawElement(Matrix4 matrix, IElement element){
        ShaderProgram backgroundShader = element.getStylings().getBackgroundShader();
        backgroundShader.bind();
        backgroundShader.setUniformMatrix("u_projTrans", matrix);
        element.getMesh().render(backgroundShader, GL20.GL_TRIANGLES);
    }
}