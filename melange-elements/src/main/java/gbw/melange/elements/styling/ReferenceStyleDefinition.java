package gbw.melange.elements.styling;

import gbw.melange.mesh.modifiers.BevelConfig;
import gbw.melange.common.shading.constants.GLDrawStyle;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.shading.ManagedShader;
import gbw.melange.common.shading.postprocess.IPostProcessShader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GustavBW
 */
public class ReferenceStyleDefinition implements IReferenceStyleDefinition {

    public static final IReferenceStyleDefinition DEFAULT = new ReferenceStyleDefinition();
    public static final IReferenceStyleDefinition NONE = new ReferenceStyleDefinition();
    static {
        NONE.borderShader(ManagedShader.DEFAULT);
        NONE.backgroundShader(ManagedShader.DEFAULT);
        NONE.backgroundDrawStyle(GLDrawStyle.POINTS);
        NONE.borderDrawStyle(GLDrawStyle.POINTS);
    }
    private final List<IPostProcessShader> postProcesses = new ArrayList<>();
    private IManagedShader<?> backgroundShader = ManagedShader.DEFAULT;
    private IManagedShader<?> borderShader = ManagedShader.DEFAULT;
    private GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    private GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;
    private BevelConfig bevelConfig = BevelConfig.DEFAULT;

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
    public IManagedShader<?> backgroundShader() {
        return backgroundShader;
    }

    /** {@inheritDoc} */
    @Override
    public void backgroundShader(IManagedShader<?> program) {
        this.backgroundShader = program;
    }

    /** {@inheritDoc} */
    @Override
    public IManagedShader<?> borderShader() {
        return borderShader;
    }

    /** {@inheritDoc} */
    @Override
    public void borderShader(IManagedShader<?> program) {
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
    public List<IPostProcessShader> postProcesses() {
        return postProcesses;
    }
}
