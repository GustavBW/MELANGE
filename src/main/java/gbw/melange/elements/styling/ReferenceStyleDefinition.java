package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IReferenceStyleDefinition;
import gbw.melange.shading.templating.ShaderProgramBuilder;

public class ReferenceStyleDefinition implements IReferenceStyleDefinition {
    public static final ReferenceStyleDefinition DEFAULT = new ReferenceStyleDefinition();

    public ShaderProgram backgroundShader = ShaderProgramBuilder.DEFAULT;
    public ShaderProgram borderShader = ShaderProgramBuilder.DEFAULT;
    public GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    public GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;

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
    }

    @Override
    public ShaderProgram backgroundShader() {
        return backgroundShader;
    }

    @Override
    public void backgroundShader(ShaderProgram program) {
        this.backgroundShader = program;
    }

    @Override
    public ShaderProgram borderShader() {
        return borderShader;
    }

    @Override
    public void borderShader(ShaderProgram program) {
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
}
