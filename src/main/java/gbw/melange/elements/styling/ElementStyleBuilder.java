package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementConstraintBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.builders.IPartialBuilder;
import gbw.melange.common.elementary.IReferenceStyleDefinition;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;

public class ElementStyleBuilder<T> implements IElementStyleBuilder<T> {
    private IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private final IElementBuilder<T> parentBuilder;
    public ElementStyleBuilder(IElementBuilder<T> parentBuilder){
        this.parentBuilder = parentBuilder;
    }
    @Override
    public IElementBuilder<T> apply() {
        parentBuilder.stylingsFrom(referenceStyling);
        return parentBuilder;
    }
    @Override
    public IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style) {
        referenceStyling.backgroundDrawStyle(style);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style) {
        referenceStyling.borderDrawStyle(style);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBackgroundColor(Color color) {
        return setBackgroundColor(FragmentShader.constant(color));
    }

    @Override
    public IElementStyleBuilder<T> setBorderColor(Color color) {
        return setBorderColor(FragmentShader.constant(color));
    }

    @Override
    public IElementStyleBuilder<T> setBackgroundColor(FragmentShader fragment) {
        return setBackgroundColor(new ShaderProgram(VertexShader.DEFAULT, fragment.code()));
    }

    @Override
    public IElementStyleBuilder<T> setBorderColor(FragmentShader fragment) {
        return setBorderColor(new ShaderProgram(VertexShader.DEFAULT, fragment.code()));
    }

    @Override
    public IElementStyleBuilder<T> setBackgroundColor(ShaderProgram shader) {
        referenceStyling.backgroundShader(shader);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderColor(ShaderProgram shader) {
        referenceStyling.borderShader(shader);
        return this;
    }
}
