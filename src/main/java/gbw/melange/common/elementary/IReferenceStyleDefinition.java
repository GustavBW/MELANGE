package gbw.melange.common.elementary;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;

public interface IReferenceStyleDefinition {
    ShaderProgram backgroundShader();
    void backgroundShader(ShaderProgram program);
    ShaderProgram borderShader();
    void borderShader(ShaderProgram program);
    GLDrawStyle backgroundDrawStyle();
    void backgroundDrawStyle(GLDrawStyle style);
    GLDrawStyle borderDrawStyle();
    void borderDrawStyle(GLDrawStyle style);

}
