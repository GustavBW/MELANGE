package gbw.melange.welcomeapp.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.MeshTable;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.shading.templating.gradients.InterpolationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpttgTest implements OnRender {
    private static final Logger log = LoggerFactory.getLogger(SpttgTest.class);

    ShaderProgram shader;

    private Matrix4 matrix;

    public SpttgTest() {
        matrix = new Matrix4();
        FragmentShader fragmentShader = GradientFragmentShaderBuilder.create(12)
                .addStop(Color.WHITE, 0)
                .addStop(Color.ROYAL, .5)
                .addStop(Color.CORAL, 1)
                .setInterpolationType(InterpolationType.HERMIT)
                .build();
        shader = new ShaderProgram(VertexShader.DEFAULT, fragmentShader.code());
        log.info("Shader is compiled: " + shader.isCompiled());
    }

    @Override
    public void onRender(){
        shader.bind();
        shader.setUniformMatrix("u_projTrans", matrix);

        MeshTable.EQUILATERAL_TRIANGLE.getMesh().render(shader, GL20.GL_TRIANGLES);

    }
}
