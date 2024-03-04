package gbw.melange.welcomeapp.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.MelangeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpttgTest implements OnRender {
    private static final Logger log = LoggerFactory.getLogger(SpttgTest.class);
    private String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "v_color = vec4(1, 1, 1, 1);\n" +
            "v_texCoords = a_texCoord0;\n" +
            "gl_Position =  u_projTrans * a_position;\n" +
            "}";
    private String fragmentShader = """
            #ifdef GL_ES
            precision mediump float;
            #endif
            
            varying vec4 v_color;
            varying vec2 v_texCoords;
            
            void main() {
                // Define the start (bottom) and end (top) colors of the gradient
                vec4 startColor = vec4(0.0, 0.0, 1.0, 1.0); // Blue
                vec4 endColor = vec4(1.0, 0.0, 0.0, 1.0); // Red
            
                // Interpolate between the start and end colors based on the vertical texture coordinate
                vec4 gradientColor = mix(startColor, endColor, v_texCoords.y);
            
                gl_FragColor = gradientColor;
            }
        """;
    ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);

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
        log.info("Shader is compiled: " + shader.isCompiled());
        matrix = new Matrix4();
        matrix.rotate(0,0,1,90);
        spttgTestMesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        spttgTestTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    @Override
    public void onRender(){
        shader.bind();
        shader.setUniformMatrix("u_projTrans", matrix);

        spttgTestMesh.render(shader, GL20.GL_TRIANGLES);
    }
}
