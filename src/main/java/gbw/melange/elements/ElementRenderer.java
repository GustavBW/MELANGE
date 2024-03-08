package gbw.melange.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.IElementRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ElementRenderer implements IElementRenderer {
    private static final Logger log = LoggerFactory.getLogger(ElementRenderer.class);

    public ElementRenderer(){
    }

    @Override
    public void draw(Matrix4 parentMatrix, IElement... elements) {
        for(IElement e : elements){
            drawElement(parentMatrix, e);
        }
    }

    @Override
    public void draw(Matrix4 parentMatrix, Collection<IElement> elements) {
        for(IElement e : elements){
            drawElement(parentMatrix, e);
        }
    }

    private void drawElement(Matrix4 parentMatrix, IElement element){

        Matrix4 elementMatrix = ((ComputedTransforms) element.computed()).getMatrix();
        Matrix4 appliedMatrix = new Matrix4(parentMatrix).mul(elementMatrix);

        //Border - Rendered first so that the same mesh can be reused, as the fragment of the background is drawn on top
        ShaderProgram borderShader = element.getStylings().getBorderShader();
        borderShader.bind();
        borderShader.setUniformMatrix("u_projTrans", appliedMatrix);
        Gdx.gl.glLineWidth((float) element.getConstraints().getBorderWidth());
        element.getMesh().render(borderShader, element.getStylings().getBorderDrawStyle().value);
        //TODO: Reset borderWidth so that draw style can be properly misused for creative purposes

        //Background
        ShaderProgram backgroundShader = element.getStylings().getBackgroundShader();
        backgroundShader.bind();
        backgroundShader.setUniformMatrix("u_projTrans", appliedMatrix);
        element.getMesh().render(backgroundShader, element.getStylings().getBackgroundDrawStyle().value);

        //TODO: Move this to reactive rule resolution
        element.computed().update();
        IComputedTransforms eComputed = element.computed();
        //log.info("Element translation: " + eComputed.getPositionX() +", " + eComputed.getPositionY() + " scale: " + eComputed.getWidth() + ", " + eComputed.getHeight());


        //Content
        //...
    }
}
