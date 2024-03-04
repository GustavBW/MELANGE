package gbw.melange.welcomeapp.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpttgTest implements OnRender {
    private static final Logger log = LoggerFactory.getLogger(SpttgTest.class);

    ShaderProgram shader;

    private Mesh spttgTestMesh;
    private Texture spttgTestTexture;
    private Matrix4 matrix;

    public SpttgTest(){
        spttgTestMesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
        spttgTestMesh.setVertices(new float[] {
                -0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
                0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1,
                0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
                -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0
        });
        matrix = new Matrix4();
        spttgTestMesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        spttgTestTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        FragmentShader fragmentShader = GradientFragmentShaderBuilder.create(12)
                .addStop(Color.WHITE, 0)
                .addStop(Color.BLACK, .5)
                .addStop(Color.WHITE, 1)
                .build();
        shader = new ShaderProgram(VertexShader.DEFAULT, fragmentShader.code());
        log.info("Shader is compiled: " + shader.isCompiled());
    }

    @Override
    public void onRender(){
        shader.bind();
        shader.setUniformMatrix("u_projTrans", matrix);


        spttgTestMesh.render(shader, GL20.GL_TRIANGLES);
    }
}
