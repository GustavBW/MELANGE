package gbw.melange.elements.styling;

import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.WrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.ArrayList;
import java.util.List;

public class ReferenceStyleDefinition implements IReferenceStyleDefinition {

    public static final IReferenceStyleDefinition DEFAULT = new ReferenceStyleDefinition();
    public static final IReferenceStyleDefinition NONE = new ReferenceStyleDefinition();
    static {
        NONE.borderShader(WrappedShader.NONE);
        NONE.backgroundShader(WrappedShader.NONE);
        NONE.backgroundDrawStyle(GLDrawStyle.POINTS);
        NONE.borderDrawStyle(GLDrawStyle.POINTS);
    }
    public final List<PostProcessShader> postProcesses = new ArrayList<>();
    public IWrappedShader backgroundShader = WrappedShader.DEFAULT;
    public IWrappedShader borderShader = WrappedShader.DEFAULT;
    public GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    public GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;
    public BevelConfig bevelConfig = BevelConfig.DEFAULT;

    public ReferenceStyleDefinition(){}

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

    @Override
    public IWrappedShader backgroundShader() {
        return backgroundShader;
    }

    @Override
    public void backgroundShader(IWrappedShader program) {
        this.backgroundShader = program;
    }

    @Override
    public IWrappedShader borderShader() {
        return borderShader;
    }

    @Override
    public void borderShader(IWrappedShader program) {
        this.borderShader = program;
    }

    @Override
    public GLDrawStyle backgroundDrawStyle() {
        return backgroundDrawStyle;
    }

    @Override
    public void backgroundDrawStyle(GLDrawStyle style) {
        this.backgroundDrawStyle = style;
    }

    @Override
    public GLDrawStyle borderDrawStyle() {
        return borderDrawStyle;
    }

    @Override
    public void borderDrawStyle(GLDrawStyle style) {
        this.borderDrawStyle = style;
    }

    @Override
    public BevelConfig borderBevel() {
        return bevelConfig;
    }

    @Override
    public void borderBevel(BevelConfig config) {
        this.bevelConfig = config;
    }

    @Override
    public List<PostProcessShader> postProcesses() {
        return postProcesses;
    }
}
