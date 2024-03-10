package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.elementary.styling.IBevelOperation;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.ShaderProgramWrapper;
import gbw.melange.shading.VertexShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

public class ElementStyleBuilder<T> implements IElementStyleBuilder<T> {
    private final IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private final IElementBuilder<T> parentBuilder;
    private final int expectedElementId;
    public ElementStyleBuilder(IElementBuilder<T> parentBuilder, int expectedElementId){
        this.parentBuilder = parentBuilder;
        this.expectedElementId = expectedElementId;
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
        return setBackgroundColor(new ShaderProgramWrapper(generateShaderLocalName("background"), VertexShader.DEFAULT, fragment));
    }

    @Override
    public IElementStyleBuilder<T> setBorderColor(FragmentShader fragment) {
        return setBorderColor(new ShaderProgramWrapper(generateShaderLocalName("border"), VertexShader.DEFAULT, fragment));
    }

    private String generateShaderLocalName(String type){
        return "Element(" + expectedElementId + ") " + type + " shader";
    }

    @Override
    public IElementStyleBuilder<T> setBackgroundColor(ShaderProgramWrapper shader) {
        referenceStyling.backgroundShader(shader);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderColor(ShaderProgramWrapper shader) {
        referenceStyling.borderShader(shader);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderRadius(BevelConfig config) {
        referenceStyling.borderBevel(config);
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderRadius(double width) {
        referenceStyling.borderBevel(new BevelConfig(BevelConfig.DEFAULT.subdivs(), BevelConfig.DEFAULT.angleThreshold(), width));
        return this;
    }

    @Override
    public IElementStyleBuilder<T> setBorderRadius(double width, int subdivs) {
        referenceStyling.borderBevel(new BevelConfig(subdivs, BevelConfig.DEFAULT.angleThreshold(), width));
        return this;
    }

    @Override
    public IElementStyleBuilder<T> addPostProcess(PostProcessShader postProcess) {

        referenceStyling.postProcesses().add(postProcess);
        return this;
    }
}
