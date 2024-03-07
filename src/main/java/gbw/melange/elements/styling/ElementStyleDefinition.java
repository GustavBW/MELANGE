package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.common.elementary.IReferenceStyleDefinition;
import gbw.melange.shading.templating.ShaderProgramBuilder;

public class ElementStyleDefinition implements IElementStyleDefinition {

    private ShaderProgram backgroundShader = ShaderProgramBuilder.DEFAULT;
    private ShaderProgram borderShader = ShaderProgramBuilder.DEFAULT;
    private GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    private GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;

    public ElementStyleDefinition(IReferenceStyleDefinition def){
        if(def.backgroundShader()     != null)    this.backgroundShader       = def.backgroundShader();
        if(def.borderShader()         != null)    this.borderShader           = def.borderShader();
        if(def.backgroundDrawStyle()  != GLDrawStyle.INVALID)      this.backgroundDrawStyle    = def.backgroundDrawStyle();
        if(def.borderDrawStyle()     != GLDrawStyle.INVALID)      this.borderDrawStyle        = def.borderDrawStyle();
    }

    @Override
    public ShaderProgram getBackgroundShader() {
        return backgroundShader;
    }

    @Override
    public ShaderProgram getBorderShader() {
        return borderShader;
    }

    @Override
    public GLDrawStyle getBackgroundDrawStyle() {
        return backgroundDrawStyle;
    }

    @Override
    public GLDrawStyle getBorderDrawStyle() {
        return borderDrawStyle;
    }

    @Override
    public void dispose(){
        if(backgroundShader != null){
            backgroundShader.dispose();
        }
        if(borderShader != null){
            borderShader.dispose();
        }

    }
}
