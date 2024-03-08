package gbw.melange.common.builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.shading.FragmentShader;

public interface IElementStyleBuilder<T> extends IPartialBuilder<IElementBuilder<T>> {

    IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBackgroundColor(Color color);
    IElementStyleBuilder<T> setBorderColor(Color color);
    IElementStyleBuilder<T> setBackgroundColor(FragmentShader fragment);
    IElementStyleBuilder<T> setBorderColor(FragmentShader fragment);
    IElementStyleBuilder<T> setBackgroundColor(ShaderProgram shader);
    IElementStyleBuilder<T> setBorderColor(ShaderProgram shader);
    IElementStyleBuilder<T> setBorderRadius(BevelConfig config);
    IElementStyleBuilder<T> setBorderRadius(double width);
    IElementStyleBuilder<T> setBorderRadius(double width, int subdivs);
}
