package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.shading.templating.ShaderProgramBuilder;
import org.lwjgl.opengl.GL20;

public class ElementStyleDefinition implements IElementStyleDefinition {

    private ShaderProgram backgroundShader = ShaderProgramBuilder.DEFAULT;
    private ShaderProgram borderShader = ShaderProgramBuilder.DEFAULT;
    private int backgroundDrawStyle = GL20.GL_TRIANGLES;
    private int borderDrawStyle = GL20.GL_LINE_LOOP;

    public ElementStyleDefinition(ReferenceStyleDefinition def){
        if(def.backgroundShader     != null)    this.backgroundShader       = def.backgroundShader;
        if(def.borderShader         != null)    this.borderShader           = def.borderShader;
        if(def.backgroundDrawStyle  != -1)      this.backgroundDrawStyle    = def.backgroundDrawStyle;
        if(def.borderDrawStyle      != -1)      this.borderDrawStyle        = def.borderDrawStyle;
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
    public int getBackgroundDrawStyle() {
        return backgroundDrawStyle;
    }

    @Override
    public int getBorderDrawStyle() {
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
