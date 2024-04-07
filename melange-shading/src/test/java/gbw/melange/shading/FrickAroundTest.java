package gbw.melange.shading;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.constants.GLShaderAttr;
import gbw.melange.common.errors.Errors;
import gbw.melange.shading.generative.checker.CheckerFragmentBuilder;
import gbw.melange.common.shading.generative.checker.ICheckerShader;
import gbw.melange.common.shading.services.IShaderPipeline;
import gbw.melange.shading.services.ShaderPipeline;
import gbw.melange.shading.services.ShadingPipelineConfig;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL30;

public class FrickAroundTest extends ApplicationAdapter {


@Test
    public void runItAndSeeIfItWorks(){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setInitialVisible(true);
        config.enableGLDebugOutput(true, System.out);
        config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL32, 3, 3);

        new Lwjgl3Application(new FrickAroundTest(), config);
    }

    @Override
    public void create() {
        leQuad = getScreenQuad();
        leShadour = getShader();

        Gdx.gl.glDisable(GL30.GL_DEPTH_TEST);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glEnable(GL30.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha
    }

    private Mesh leQuad;
    private IManagedShader<?> leShadour;
    private Matrix4 beaubatrix = new Matrix4().idt();

    @Override
    public void render(){
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        leShadour.getProgram().bind();
        leShadour.applyBindings();
        leShadour.getProgram().setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), beaubatrix);

        // Render the quad
        leQuad.render(leShadour.getProgram(), GL30.GL_TRIANGLES);

    }


    private static IManagedShader<?> getShader(){
        IShaderPipeline pipeline = new ShaderPipeline(new ShadingPipelineConfig().clearGeneratedOnStart(true));

        ICheckerShader shader = new CheckerFragmentBuilder("leShadour", pipeline)
                .setRows(2)
                .setColumns(2)
                .setFirstColor(Color.FIREBRICK)
                .setSecondColor(Color.OLIVE)
                .build();


        Errors.escalate(pipeline::compileAndCache);

        return shader;
    }


    private static Mesh getScreenQuad(){
        // Define the vertices with UV coordinates
        // Vertex format: X, Y, U, V
        float[] vertices = {
                // X    Y    U    V
                -1f, -1f, 0f, 0f, // Bottom Left corner
                1f, -1f, 1f, 0f, // Bottom Right corner
                1f,  1f, 1f, 1f, // Top Right corner
                -1f,  1f, 0f, 1f  // Top Left corner
        };

        // Define the indices that compose the quad
        short[] indices = {0, 1, 2, 2, 3, 0};

        // Create the mesh with Position and Texture Coordinates (UV)
        Mesh quad = new Mesh(true, 4, 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, GLShaderAttr.POSITION.glValue()),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, GLShaderAttr.TEX_COORD0.glValue()));

        quad.setVertices(vertices);
        quad.setIndices(indices);

        return quad;
    }

}
