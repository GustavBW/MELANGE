package gbw.melange.shading.templating;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;

public class ShaderProgramBuilder {
    public static final ShaderProgram DEFAULT = new ShaderProgram(VertexShader.DEFAULT, FragmentShader.DEFAULT);

}
