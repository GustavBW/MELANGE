package gbw.melange.mesh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.errors.Errors;
import gbw.melange.mesh.services.IMeshPipeline;
import gbw.melange.mesh.services.MeshPipeline;
import gbw.melange.mesh.services.ShapeService;
import gbw.melange.mesh.services.Shapes;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.components.FragmentShader;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.generative.checker.CheckerFragmentBuilder;
import gbw.melange.shading.generative.checker.CheckerShader;
import gbw.melange.shading.services.*;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL30;

public class FrickAroundTest extends ApplicationAdapter {

    @Test
    public void lookAroundAndDiscover(){
     Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
     config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL32, 3, 3);
     new Lwjgl3Application(new FrickAroundTest(), config);
    }

    private IManagedMesh leMaesh;
    private IManagedShader<?> leShadour;
    private IManagedShader<?> debugUV;
    private Matrix4 beaubatrix = new Matrix4().idt();
    @Override
    public void create(){
        IShaderPipeline pipeline = new ShaderPipeline(new ShadingPipelineConfig());
        Colors colorService = new ColorService(pipeline);

        leShadour = colorService.checker()
                .setRows(8)
                .setColumns(8)
                .build();

        debugUV = colorService.fromFragment(FragmentShader.DEBUG_UV);

        Errors.escalate(pipeline::compileAndCache);

        IMeshPipeline meshPipeline = new MeshPipeline();
        Shapes shapeService = new ShapeService(meshPipeline);

        leMaesh = shapeService.square();

        Gdx.gl.glDisable(GL30.GL_DEPTH_TEST);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glEnable(GL30.GL_BLEND); // Enable blending for transparency
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA); // Standard blending mode for premultiplied alpha

    }

    @Override
    public void render(){
        // Ensure depth testing is disabled for 2D rendering
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        debugUV.getProgram().bind();
        debugUV.applyBindings();
        debugUV.getProgram().setUniformMatrix(GLShaderAttr.PROJECTION_MATRIX.glValue(), beaubatrix);

        // Render the quad
        leMaesh.getMesh().render(debugUV.getProgram(), GL30.GL_TRIANGLES);

        // Re-enable depth testing if you disabled it before
        // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    }


}
