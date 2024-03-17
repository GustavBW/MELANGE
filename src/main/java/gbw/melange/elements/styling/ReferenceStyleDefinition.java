package gbw.melange.elements.styling;

import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.impl.WrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ReferenceStyleDefinition class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ReferenceStyleDefinition implements IReferenceStyleDefinition {

    /** Constant <code>DEFAULT</code> */
    public static final IReferenceStyleDefinition DEFAULT = new ReferenceStyleDefinition();
    /** Constant <code>NONE</code> */
    public static final IReferenceStyleDefinition NONE = new ReferenceStyleDefinition();
    static {
        NONE.borderShader(WrappedShader.DEFAULT);
        NONE.backgroundShader(WrappedShader.DEFAULT);
        NONE.backgroundDrawStyle(GLDrawStyle.POINTS);
        NONE.borderDrawStyle(GLDrawStyle.POINTS);
    }
    public final List<PostProcessShader> postProcesses = new ArrayList<>();
    public IWrappedShader backgroundShader = WrappedShader.DEFAULT;
    public IWrappedShader borderShader = WrappedShader.DEFAULT;
    public GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    public GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;
    public BevelConfig bevelConfig = BevelConfig.DEFAULT;

    /**
     * <p>Constructor for ReferenceStyleDefinition.</p>
     */
    public ReferenceStyleDefinition(){}

    /**
     * <p>Constructor for ReferenceStyleDefinition.</p>
     *
     * @param reference a {@link gbw.melange.common.elementary.styling.IElementStyleDefinition} object
     */
    public ReferenceStyleDefinition(IElementStyleDefinition reference){
        if(reference.getBackgroundShader() != null){
            this.backgroundShader = reference.getBackgroundShader();
        }
        if(reference.getBorderShader() != null){
            this.borderShader = reference.getBorderShader();
        }
        if(reference.getBackgroundDrawStyle() != GLDrawStyle.INVALID){
            this.backgroundDrawStyle = reference.getBackgroundDrawStyle();
        }
        if(reference.getBorderDrawStyle() != GLDrawStyle.INVALID){
            this.borderDrawStyle = reference.getBorderDrawStyle();
        }
        if(reference.getBorderBevel() != null){
            this.bevelConfig = reference.getBorderBevel();
        }
        if(reference.getPostProcesses() != null){
            this.postProcesses.addAll(reference.getPostProcesses());
        }
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader backgroundShader() {
        return backgroundShader;
    }

    /** {@inheritDoc} */
    @Override
    public void backgroundShader(IWrappedShader program) {
        this.backgroundShader = program;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader borderShader() {
        return borderShader;
    }

    /** {@inheritDoc} */
    @Override
    public void borderShader(IWrappedShader program) {
        this.borderShader = program;
    }

    /** {@inheritDoc} */
    @Override
    public GLDrawStyle backgroundDrawStyle() {
        return backgroundDrawStyle;
    }

    /** {@inheritDoc} */
    @Override
    public void backgroundDrawStyle(GLDrawStyle style) {
        this.backgroundDrawStyle = style;
    }

    /** {@inheritDoc} */
    @Override
    public GLDrawStyle borderDrawStyle() {
        return borderDrawStyle;
    }

    /** {@inheritDoc} */
    @Override
    public void borderDrawStyle(GLDrawStyle style) {
        this.borderDrawStyle = style;
    }

    /** {@inheritDoc} */
    @Override
    public BevelConfig borderBevel() {
        return bevelConfig;
    }

    /** {@inheritDoc} */
    @Override
    public void borderBevel(BevelConfig config) {
        this.bevelConfig = config;
    }

    /** {@inheritDoc} */
    @Override
    public List<PostProcessShader> postProcesses() {
        return postProcesses;
    }
}
