package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.ShaderProgramWrapper;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.List;

public interface IReferenceStyleDefinition {
    ShaderProgramWrapper backgroundShader();
    void backgroundShader(ShaderProgramWrapper program);
    ShaderProgramWrapper borderShader();
    void borderShader(ShaderProgramWrapper program);
    GLDrawStyle backgroundDrawStyle();
    void backgroundDrawStyle(GLDrawStyle style);
    GLDrawStyle borderDrawStyle();
    void borderDrawStyle(GLDrawStyle style);
    BevelConfig borderBevel();
    void borderBevel(BevelConfig op);
    List<PostProcessShader> postProcesses();


}
