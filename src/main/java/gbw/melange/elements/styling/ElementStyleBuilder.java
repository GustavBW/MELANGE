package gbw.melange.elements.styling;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

/**
 * <p>ElementStyleBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementStyleBuilder<T> implements IElementStyleBuilder<T> {
    private final IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private final IElementBuilder<T> parentBuilder;
    private final int expectedElementId;
    /**
     * <p>Constructor for ElementStyleBuilder.</p>
     *
     * @param parentBuilder a {@link gbw.melange.common.builders.IElementBuilder} object
     * @param expectedElementId a int
     */
    public ElementStyleBuilder(IElementBuilder<T> parentBuilder, int expectedElementId){
        this.parentBuilder = parentBuilder;
        this.expectedElementId = expectedElementId;
    }
    /** {@inheritDoc} */
    @Override
    public IElementBuilder<T> apply() {
        parentBuilder.stylingsFrom(referenceStyling);
        return parentBuilder;
    }
    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style) {
        referenceStyling.backgroundDrawStyle(style);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style) {
        referenceStyling.borderDrawStyle(style);
        return this;
    }

    private String generateShaderLocalName(String type){
        return "Element(" + expectedElementId + ") " + type + " shader";
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBackgroundColor(IWrappedShader shader) {
        referenceStyling.backgroundShader(shader);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderColor(IWrappedShader shader) {
        referenceStyling.borderShader(shader);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderRadius(BevelConfig config) {
        referenceStyling.borderBevel(config);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderRadius(double width) {
        referenceStyling.borderBevel(new BevelConfig(BevelConfig.DEFAULT.subdivs(), BevelConfig.DEFAULT.angleThreshold(), width));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderRadius(double width, int subdivs) {
        referenceStyling.borderBevel(new BevelConfig(subdivs, BevelConfig.DEFAULT.angleThreshold(), width));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> addPostProcess(PostProcessShader postProcess) {

        referenceStyling.postProcesses().add(postProcess);
        return this;
    }
}
