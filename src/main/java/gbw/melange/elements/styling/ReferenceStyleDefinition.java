package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.shading.templating.ShaderProgramBuilder;
import org.lwjgl.opengl.GL20;

public class ReferenceStyleDefinition {
    public static final ReferenceStyleDefinition DEFAULT = new ReferenceStyleDefinition();

    public ShaderProgram backgroundShader = ShaderProgramBuilder.DEFAULT;
    public ShaderProgram borderShader = ShaderProgramBuilder.DEFAULT;
    public int backgroundDrawStyle = GL20.GL_TRIANGLES;
    public int borderDrawStyle = GL20.GL_LINE_LOOP;

    public ReferenceStyleDefinition(){}

    public ReferenceStyleDefinition(IElementStyleDefinition reference){
        if(reference.getBackgroundShader() != null){
            this.backgroundShader = reference.getBackgroundShader();
        }
        if(reference.getBorderShader() != null){
            this.borderShader = reference.getBorderShader();
        }
        if(reference.getBackgroundDrawStyle() != -1){
            this.backgroundDrawStyle = reference.getBackgroundDrawStyle();
        }
        if(reference.getBorderDrawStyle() != -1){
            this.borderDrawStyle = reference.getBorderDrawStyle();
        }
    }

}
