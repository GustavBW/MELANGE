package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.elements.styling.ElementStyleDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;
import gbw.melange.shading.ShaderProgramWrapper;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.List;

public interface IElementStyleDefinition extends Disposable {
    IElementStyleDefinition DEFAULT = new ElementStyleDefinition(ReferenceStyleDefinition.DEFAULT);

    ShaderProgramWrapper getBackgroundShader();
    ShaderProgramWrapper getBorderShader();

    GLDrawStyle getBackgroundDrawStyle(); //TODO: Swap to Enum
    GLDrawStyle getBorderDrawStyle();//TODO: Swap to Enum

    BevelConfig getBorderBevel();
    List<PostProcessShader> getPostProcesses();

}
