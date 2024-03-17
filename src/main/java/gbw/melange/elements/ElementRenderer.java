package gbw.melange.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.shading.errors.Errors;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.shading.impl.WrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * <p>ElementRenderer class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementRenderer implements IElementRenderer {
    private static final Logger log = LogManager.getLogger();


    /**
     * <p>Constructor for ElementRenderer.</p>
     */
    public ElementRenderer(){}

    /** {@inheritDoc} */
    @Override
    public void draw(Matrix4 parentMatrix, IElement<?>... elements) {
        for(IElement<?> e : elements){
            draw0(parentMatrix, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void draw(Matrix4 parentMatrix, Collection<IElement<?>> elements) {
        for(IElement<?> e : elements){
            draw0(parentMatrix, e);
        }
    }


    private void draw0(Matrix4 parentMatrix, IElement<?> element){

        ComputedTransforms computed = ((ComputedTransforms) element.computed());
        Matrix4 elementMatrix = computed.getMatrix();
        Matrix4 appliedMatrix = new Matrix4(parentMatrix).mul(elementMatrix);

        FrameBuffer fboA = ((Element<?>) element).getComputedShading().getFrameBufferA();
        FrameBuffer fboB = ((Element<?>) element).getComputedShading().getFrameBufferB();

        //The main pass is rendered to the FBO to enable post-process effects
        mainRenderPass(fboA, element, appliedMatrix);

        FrameBuffer finalFBO = postProcessPass(element, fboA, fboB, appliedMatrix);

        outputToScreen(finalFBO, element, appliedMatrix);

        //TODO: Move this to reactive rule resolution
        computed.update();
        //log.info(element + ": " + computed.getPositionX() +", " + computed.getPositionY() + " scale: " + computed.getWidth() + ", " + computed.getHeight());


        //Content
        //...
    }

    private static void outputToScreen(FrameBuffer fbo, IElement<?> element, Matrix4 appliedMatrix){
        // The long way of clearing only some of the screen
        final double[] bounds = element.computed().getAxisAlignedBounds();
        final double appWidth = Gdx.graphics.getWidth(), appHeight = Gdx.graphics.getHeight();
        /*
        Gdx.gl.glEnable(GL30.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(
                (int) (bounds[0] * appWidth),
                (int) (bounds[1] * appHeight),
                (int) (bounds[2] * appWidth),
                (int) (bounds[3] * appHeight)
        );
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL30.GL_SCISSOR_TEST);
        */
        Gdx.gl.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); // Bind the default framebuffer
        fbo.getColorBufferTexture().bind(69);

        ShaderProgram finalShader = WrappedShader.TEXTURE.getProgram();
        finalShader.bind();
        finalShader.setUniformMatrix("u_projTrans", appliedMatrix);
        fbo.getColorBufferTexture().bind(69);
        finalShader.setUniformi("u_texture", 69);

        element.getMesh().render(finalShader, GLDrawStyle.TRIANGLES.value);
    }

    private static FrameBuffer postProcessPass(IElement<?> element, FrameBuffer fboA, FrameBuffer fboB, Matrix4 appliedMatrix) {
        if (element.getStylings().getPostProcesses().isEmpty()) return fboA;

        boolean flip = true; // Determine which FBO is the source and which is the destination

        for (PostProcessShader shader : element.getStylings().getPostProcesses()) {
            if (flip) {
                fboB.begin(); // Write to B
            } else {
                fboA.begin(); // Write to A
            }

            // Clear the FBO before rendering to it
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

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
        IElementStyleDefinition style = element.getStylings();
        Mesh mesh = element.getMesh();

        fbo.begin();

        //Clear to transparent
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        //Border - Rendered first so that the same mesh can be reused, as the fragment of the background is drawn on top
        ShaderProgram borderShader = style.getBorderShader().getProgram();
        style.getBorderShader().applyBindings();
        borderShader.bind();
        borderShader.setUniformMatrix("u_projTrans", appliedMatrix);

        Gdx.gl.glLineWidth((float) element.getConstraints().getBorderWidth());
        Errors.checkAndThrow( " | main render pass | setting line width to:\t" + element.getConstraints().getBorderWidth());

        mesh.render(borderShader, style.getBorderDrawStyle().value);
        Errors.checkAndThrow(" | main render pass | border shader:\t" + style.getBorderShader().shortName());

        Gdx.gl.glLineWidth(1f);
        Errors.checkAndThrow(" | main render pass | resetting line width");

        //Background
        ShaderProgram backgroundShader = style.getBackgroundShader().getProgram();
        style.getBackgroundShader().applyBindings();
        backgroundShader.bind();
        backgroundShader.setUniformMatrix("u_projTrans", appliedMatrix);
        mesh.render(backgroundShader, style.getBackgroundDrawStyle().value);

        int glErrCausedByBackgroundShader = Gdx.gl.glGetError();
        if(glErrCausedByBackgroundShader != GL30.GL_NO_ERROR){
            log.warn("OpenGL Error | main render pass | background shader:\t" + style.getBackgroundShader().shortName() + " code: " + glErrCausedByBackgroundShader);
        }

        fbo.end();
    }
}
