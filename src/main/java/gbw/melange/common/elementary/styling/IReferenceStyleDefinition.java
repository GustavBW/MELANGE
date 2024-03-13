package gbw.melange.common.elementary.styling;

import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.List;

public interface IReferenceStyleDefinition {
    IWrappedShader backgroundShader();
    void backgroundShader(IWrappedShader program);
    IWrappedShader borderShader();
    void borderShader(IWrappedShader program);
    GLDrawStyle backgroundDrawStyle();
    void backgroundDrawStyle(GLDrawStyle style);
    GLDrawStyle borderDrawStyle();
    void borderDrawStyle(GLDrawStyle style);
    BevelConfig borderBevel();
    void borderBevel(BevelConfig op);
    List<PostProcessShader> postProcesses();


}
