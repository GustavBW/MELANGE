package gbw.melange.common.builders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.FragmentShader;

public interface IElementStyleBuilder<T> extends IPartialBuilder<IElementBuilder<T>> {

    IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBackgroundColor(FragmentShader fragment);
    IElementStyleBuilder<T> setBorderColor(FragmentShader fragment);
    IElementStyleBuilder<T> setBackgroundColor(ShaderProgram shader);
    IElementStyleBuilder<T> setBorderColor(ShaderProgram shader);
}
