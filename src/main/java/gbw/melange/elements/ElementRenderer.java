package gbw.melange.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.postprocessing.PostProcessShader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ElementRenderer implements IElementRenderer {
    private static final Logger log = LoggerFactory.getLogger(ElementRenderer.class);

    private final SpriteBatch batch = new SpriteBatch();

    public ElementRenderer(){}

    @Override
    public void draw(Matrix4 parentMatrix, IElement... elements) {
        batch.begin();
        for(IElement e : elements){
            drawElementToBatch(batch, parentMatrix, e);
        }
        batch.end();
    }

    @Override
    public void draw(Matrix4 parentMatrix, Collection<IElement> elements) {
        batch.begin();
        for(IElement e : elements){
            drawElementToBatch(batch, parentMatrix, e);
        }
        batch.end();
    }


    private void drawElementToBatch(SpriteBatch batch, Matrix4 parentMatrix, IElement element){

        Matrix4 elementMatrix = ((ComputedTransforms) element.computed()).getMatrix();
        Matrix4 appliedMatrix = new Matrix4(parentMatrix).mul(elementMatrix);

        FrameBuffer elementFBO = ((ComputedTransforms) element.computed()).getFrameBuffer();
        elementFBO.begin();

        //Clear to black
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //The main pass is rendered to the FBO to enable post-process effects
        mainRenderPass(element, appliedMatrix);

        elementFBO.end();

        TextureRegion region = ((ComputedTransforms) element.computed()).getTextureRegion();
        postProcessPass(region, element, elementFBO, appliedMatrix);

        batch.draw(region, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //TODO: Move this to reactive rule resolution
        element.computed().update();
        IComputedTransforms eComputed = element.computed();
        //log.info("Element translation: " + eComputed.getPositionX() +", " + eComputed.getPositionY() + " scale: " + eComputed.getWidth() + ", " + eComputed.getHeight());


        //Content
        //...
    }

    private static void postProcessPass(TextureRegion region, IElement element, FrameBuffer elementFBO, Matrix4 appliedMatrix) {
        region.setTexture(elementFBO.getColorBufferTexture());
        region.flip(false, true);

        for(PostProcessShader postShader : element.getStylings().getPostProcesses()){
            postShader.program().bind();
            postShader.program().setUniformMatrix("u_projTrans", appliedMatrix);
            region.getTexture().bind();
            element.getMesh().render(postShader.program(), GLDrawStyle.TRIANGLES.value);
        }
    }


    private static void mainRenderPass(IElement element, Matrix4 appliedMatrix) {
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
    }
}
