package gbw.melange.elements.styling;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.builders.IElementStyleBuilder;
import gbw.melange.common.mesh.modifiers.IBevelConfig;
import gbw.melange.mesh.modifiers.BevelConfig;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.shading.constants.GLDrawStyle;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.postprocess.IPostProcessShader;

/**
 * <p>ElementStyleBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementStyleBuilder<T> implements IElementStyleBuilder<T> {
    private final IReferenceStyleDefinition referenceStyling = new ReferenceStyleDefinition();
    private final IElementBuilder<T> parentBuilder;
    /**
     * <p>Constructor for ElementStyleBuilder.</p>
     *
     * @param parentBuilder a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    public ElementStyleBuilder(IElementBuilder<T> parentBuilder){
        this.parentBuilder = parentBuilder;
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

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBackgroundColor(IManagedShader<?> shader) {
        referenceStyling.backgroundShader(shader);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderColor(IManagedShader<?> shader) {
        referenceStyling.borderShader(shader);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleBuilder<T> setBorderRadius(IBevelConfig config) {
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
    public IElementStyleBuilder<T> addPostProcess(IPostProcessShader postProcess) {
        referenceStyling.postProcesses().add(postProcess);
        return this;
    }
}
