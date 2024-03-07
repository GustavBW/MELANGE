package gbw.melange.common.elementary;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.elements.styling.ElementStyleDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;

public interface IElementStyleDefinition extends Disposable {
    IElementStyleDefinition DEFAULT = new ElementStyleDefinition(ReferenceStyleDefinition.DEFAULT);

    ShaderProgram getBackgroundShader();
    ShaderProgram getBorderShader();

    GLDrawStyle getBackgroundDrawStyle(); //TODO: Swap to Enum
    GLDrawStyle getBorderDrawStyle();//TODO: Swap to Enum

}
