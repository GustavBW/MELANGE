package gbw.melange.shading.templating;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;

public class ShaderProgramBuilder {
    public static final ShaderProgram DEFAULT = new ShaderProgram(VertexShader.DEFAULT.code(), FragmentShader.DEFAULT.code());
    public static final ShaderProgram NONE = new ShaderProgram(VertexShader.NONE.code(), FragmentShader.TRANSPARENT.code());

}
