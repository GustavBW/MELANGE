package gbw.melange.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.ShaderProgramWrapper;
import gbw.melange.shading.postprocessing.PostProcessShader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class ElementRenderer implements IElementRenderer {
    private static final Logger log = LoggerFactory.getLogger(ElementRenderer.class);

    private final SpriteBatch batch = new SpriteBatch();

    public ElementRenderer(){}

    @Override
    public void draw(Matrix4 parentMatrix, IElement<?>... elements) {
        for(IElement<?> e : elements){
            drawElementToBatch(batch, parentMatrix, e);
        }
    }

    @Override
    public void draw(Matrix4 parentMatrix, Collection<IElement<?>> elements) {
        for(IElement<?> e : elements){
            drawElementToBatch(batch, parentMatrix, e);
        }
    }


    private void drawElementToBatch(SpriteBatch batch, Matrix4 parentMatrix, IElement<?> element){

        Matrix4 elementMatrix = ((ComputedTransforms) element.computed()).getMatrix();
        Matrix4 appliedMatrix = new Matrix4(parentMatrix).mul(elementMatrix);

        FrameBuffer fboA = ((ComputedTransforms) element.computed()).getFrameBufferA();
        FrameBuffer fboB = ((ComputedTransforms) element.computed()).getFrameBufferB();

        //The main pass is rendered to the FBO to enable post-process effects
        mainRenderPass(fboA, element, appliedMatrix);

        FrameBuffer finalFBO = postProcessPass(element, fboA, fboB, appliedMatrix);

        outputToScreen(finalFBO, element, appliedMatrix);

        IComputedTransforms computed = element.computed();

        //TODO: Move this to reactive rule resolution
        element.computed().update();
        IComputedTransforms eComputed = element.computed();
        //log.info(element + ": " + eComputed.getPositionX() +", " + eComputed.getPositionY() + " scale: " + eComputed.getWidth() + ", " + eComputed.getHeight());


        //Content
        //...
    }

    private static void outputToScreen(FrameBuffer fbo, IElement<?> element, Matrix4 appliedMatrix){
        // The long way of clearing only some of the screen
        final double[] bounds = element.computed().getAxisAlignedBounds();
        final double appWidth = Gdx.graphics.getWidth(), appHeight = Gdx.graphics.getHeight();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(
                (int) (bounds[0] * appWidth),
                (int) (bounds[1] * appHeight),
                (int) (bounds[2] * appWidth),
                (int) (bounds[3] * appHeight)
        );
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

        Gdx.gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0); // Bind the default framebuffer
        fbo.getColorBufferTexture().bind(69);

        ShaderProgram finalShader = ShaderProgramWrapper.mlg_texture().getProgram();
        finalShader.bind();
        finalShader.setUniformMatrix("u_projTrans", appliedMatrix);
        finalShader.setUniformi("u_texture", 69);

        element.getMesh().render(finalShader, GLDrawStyle.TRIANGLES.value);
    }

    private static FrameBuffer postProcessPass(IElement<?> element, FrameBuffer fboA, FrameBuffer fboB, Matrix4 appliedMatrix) {
        boolean flip = true; // Determine which FBO is the source and which is the destination

        for (PostProcessShader shader : element.getStylings().getPostProcesses()) {
            if (flip) {
                fboB.begin(); // Write to B
            } else {
                fboA.begin(); // Write to A
            }

            // Clear the FBO before rendering to it
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            // Bind the shader
            ShaderProgram shaderProgram = shader.program();
            shaderProgram.bind();
            shaderProgram.setUniformMatrix("u_projTrans", appliedMatrix);

            // Bind the texture from the other FBO
            Texture inputTexture = flip ? fboA.getColorBufferTexture() : fboB.getColorBufferTexture();
            inputTexture.bind(0);
            shaderProgram.setUniformi("u_texture", 0); // Assuming the shader samples from "u_texture"

            // Render using the full-screen quad mesh (or appropriate geometry)
            element.getMesh().render(shaderProgram, GLDrawStyle.TRIANGLES.value);

            if (flip) {
                fboB.end(); // Done writing to B
            } else {
                fboA.end(); // Done writing to A
            }

            flip = !flip; // Swap roles for the next pass
        }
        // The final result is in the FBO that is not the source
        return flip ? fboA : fboB;
    }


    private static void mainRenderPass(FrameBuffer fbo, IElement<?> element, Matrix4 appliedMatrix) {
        fbo.begin();
        //Clear to transparent
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Border - Rendered first so that the same mesh can be reused, as the fragment of the background is drawn on top
        ShaderProgram borderShader = element.getStylings().getBorderShader().getProgram();
        borderShader.bind();
        borderShader.setUniformMatrix("u_projTrans", appliedMatrix);
        Gdx.gl.glLineWidth((float) element.getConstraints().getBorderWidth());
        element.getMesh().render(borderShader, element.getStylings().getBorderDrawStyle().value);
        //TODO: Reset borderWidth so that draw style can be properly misused for creative purposes

        //Background
        ShaderProgram backgroundShader = element.getStylings().getBackgroundShader().getProgram();
        backgroundShader.bind();
        backgroundShader.setUniformMatrix("u_projTrans", appliedMatrix);
        element.getMesh().render(backgroundShader, element.getStylings().getBackgroundDrawStyle().value);

        fbo.end();
    }
}
