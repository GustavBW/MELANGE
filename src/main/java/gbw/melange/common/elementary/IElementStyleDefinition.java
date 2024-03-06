package gbw.melange.common.elementary;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.elements.styling.ElementStyleDefinition;

public interface IElementStyleDefinition {
    IElementStyleDefinition DEFAULT = new ElementStyleDefinition();

    ShaderProgram getBackgroundShader();
    ShaderProgram getBorderShader();

}
