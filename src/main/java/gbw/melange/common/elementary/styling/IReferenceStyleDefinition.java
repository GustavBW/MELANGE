package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.List;

public interface IReferenceStyleDefinition {
    ShaderProgram backgroundShader();
    void backgroundShader(ShaderProgram program);
    ShaderProgram borderShader();
    void borderShader(ShaderProgram program);
    GLDrawStyle backgroundDrawStyle();
    void backgroundDrawStyle(GLDrawStyle style);
    GLDrawStyle borderDrawStyle();
    void borderDrawStyle(GLDrawStyle style);
    BevelConfig borderBevel();
    void borderBevel(BevelConfig op);
    List<PostProcessShader> postProcesses();


}
