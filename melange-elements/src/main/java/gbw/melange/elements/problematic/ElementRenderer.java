package gbw.melange.elements.problematic;

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
import gbw.melange.common.shading.constants.GLShaderAttr;
import gbw.melange.common.errors.Errors;
import gbw.melange.common.shading.constants.GLDrawStyle;
import gbw.melange.common.shading.generative.ITexturedShader;
import gbw.melange.common.shading.postprocess.IPostProcessShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;

/**
 * <p>ElementRenderer class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementRenderer implements IElementRenderer {
    private static final Logger log = LogManager.getLogger();
    private final ITexturedShader toScreenTextureShader = TextureShader.TEXTURE.copyAs("MELANGE_INTERNAL_ER_FINAL_OTS");
    /**
     * <p>Constructor for ElementRenderer.</p>
     */
    public ElementRenderer(){
    }

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

    private final Matrix4 appliedMatrix = new Matrix4();
    private void draw0(Matrix4 parentMatrix, IElement<?> element){
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glEnable(GL30.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha

        Errors.checkAndThrow("setting blending function to GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA");

        ComputedTransforms computed = ((ComputedTransforms) element.computed());
        Matrix4 elementMatrix = computed.getMatrix();
        appliedMatrix.idt().mul(parentMatrix).mul(elementMatrix);

        FrameBuffer backgroundFboA = ((Element<?>) element).getComputedShading().getFrameBufferA();
        FrameBuffer backgroundFboB = ((Element<?>) element).getComputedShading().getFrameBufferB();
        FrameBuffer borderFbo = ((Element<?>) element).getComputedShading().getFrameBufferC();

        for(FrameBuffer fbo : List.of(backgroundFboA, backgroundFboB, borderFbo)){
            fbo.begin();
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
            fbo.end();
        }

        drawBackground(backgroundFboA, element, appliedMatrix);

        FrameBuffer finalBackgroundFbo = postProcessPass(element, backgroundFboA, backgroundFboB, appliedMatrix);

        //The main pass is rendered to the FBO to enable post-process effects
        drawBorder(borderFbo, element, appliedMatrix);

        outputToScreen(finalBackgroundFbo, borderFbo, element, appliedMatrix);

        //TODO: Move this to reactive rule resolution
        computed.update();
        //log.info(element + ": " + computed.getPositionX() +", " + computed.getPositionY() + " scale: " + computed.getWidth() + ", " + computed.getHeight());


        //Content
        //...
    }


    private static void drawBorder(FrameBuffer fbo, IElement<?> element, Matrix4 appliedMatrix) {
        IElementStyleDefinition style = element.getStylings();
        Mesh mesh = element.getMesh();

        fbo.begin();
        //Clear to transparent
        Errors.checkAndThrow("main render pass | clearing screen to color (0,0,0,0)");

        //Border - Rendered first so that the same mesh can be reused, as the fragment of the background is drawn on top
        ShaderProgram borderShader = style.getBorderShader().getProgram();
        borderShader.bind();
        style.getBorderShader().applyBindings();
        borderShader.setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), appliedMatrix);

        Gdx.gl.glLineWidth((float) element.getConstraints().getBorderWidth());
        Errors.checkAndThrow( "main render pass | setting line width to:\t" + element.getConstraints().getBorderWidth());

        mesh.render(borderShader, style.getBorderDrawStyle().glValue);
        Errors.checkAndThrow("main render pass | border shader:\t" + style.getBorderShader().getLocalName());

        fbo.end();

        Gdx.gl.glLineWidth(1f);
        Errors.checkAndThrow("main render pass | resetting line width");
    }

    private static void drawBackground(FrameBuffer fbo, IElement<?> element, Matrix4 appliedMatrix) {
        IElementStyleDefinition style = element.getStylings();
        Mesh mesh = element.getMesh();

        fbo.begin();

        //Background
        ShaderProgram backgroundShader = style.getBackgroundShader().getProgram();
        backgroundShader.bind();

        style.getBackgroundShader().applyBindings();
        backgroundShader.setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), appliedMatrix);

        mesh.render(backgroundShader, style.getBackgroundDrawStyle().glValue);

        fbo.end();

        Errors.checkAndThrow("OpenGL Error | main render pass | background shader:\t" + style.getBackgroundShader().getLocalName());
    }
    private void outputToScreen(FrameBuffer backgroundFbo, FrameBuffer borderFbo, IElement<?> element, Matrix4 appliedMatrix){
        // The long way of clearing only some of the screen
        final double[] bounds = element.computed().getAxisAlignedBounds();
        final double appWidth = Gdx.graphics.getWidth(), appHeight = Gdx.graphics.getHeight();

        Gdx.gl.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); // Bind the default framebuffer

        toScreenTextureShader.setTexture(borderFbo.getColorBufferTexture(), GLShaderAttr.TEXTURE.glValue());

        ShaderProgram finalShader = toScreenTextureShader.getProgram();
        finalShader.bind();
        toScreenTextureShader.applyBindings();
        finalShader.setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), appliedMatrix);

        element.getMesh().render(finalShader, GLDrawStyle.TRIANGLES.glValue);

        toScreenTextureShader.setTexture(backgroundFbo.getColorBufferTexture(), GLShaderAttr.TEXTURE.glValue());

        toScreenTextureShader.applyBindings();
        finalShader.setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), appliedMatrix);

        element.getMesh().render(finalShader, GLDrawStyle.TRIANGLES.glValue);
    }


    private static FrameBuffer postProcessPass(IElement<?> element, FrameBuffer fboA, FrameBuffer fboB, Matrix4 appliedMatrix) {
        boolean flip = true; // Determine which FBO is the source and which is the destination
        FrameBuffer currentlyWritingTo = fboA;
        Texture currentInputTexture;

        for (IPostProcessShader shader : element.getStylings().getPostProcesses()) {
            if (flip) {
                currentlyWritingTo = fboB;
                currentInputTexture = fboA.getColorBufferTexture();
                Errors.checkAndThrow("Error beginning FBO B during post process pass ");
            } else {
                currentlyWritingTo = fboA;
                currentInputTexture = fboB.getColorBufferTexture();
                Errors.checkAndThrow("Error beginning FBO A during post process pass ");
            }
            currentlyWritingTo.begin();
            shader.setInputTexture(currentInputTexture);

            // Bind the shader
            ShaderProgram shaderProgram = shader.getProgram();
            shaderProgram.bind();
            Errors.checkAndThrow("Unable to bind post process shader: " + shader.getLocalName());
            shaderProgram.setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), appliedMatrix);
            // Bind the texture from the other FBO
            shader.applyBindings();
            Errors.checkAndThrow("Unable to bind post process input texture: " + currentInputTexture);

            // Render using the full-screen quad mesh (or appropriate geometry)
            element.getMesh().render(shaderProgram, GLDrawStyle.TRIANGLES.glValue);
            Errors.checkAndThrow("Error rendering pps " + shader.getLocalName() + " using " + element.getMesh() + " for element " + element.getId());

            currentlyWritingTo.end();
            flip = !flip; // Swap roles for the next pass
        }

        // The final result is in the FBO that is not the source
        return currentlyWritingTo;
    }

}
