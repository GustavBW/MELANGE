package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.elementary.styling.IBevelOperation;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.shading.postprocessing.PostProcessShader;
import gbw.melange.shading.templating.ShaderProgramBuilder;

import java.util.ArrayList;
import java.util.List;

public class ElementStyleDefinition implements IElementStyleDefinition {

    private ShaderProgram backgroundShader = ShaderProgramBuilder.DEFAULT;
    private ShaderProgram borderShader = ShaderProgramBuilder.DEFAULT;
    private GLDrawStyle backgroundDrawStyle = GLDrawStyle.TRIANGLES;
    private GLDrawStyle borderDrawStyle = GLDrawStyle.LINE_LOOP;
    private BevelConfig bevelConfig = BevelConfig.DEFAULT;
    private final List<PostProcessShader> postProcesses = new ArrayList<>();

    public ElementStyleDefinition(IReferenceStyleDefinition def){
        if(def.backgroundShader() != null) this.backgroundShader = def.backgroundShader();
        if(def.borderShader() != null) this.borderShader = def.borderShader();
        if(def.backgroundDrawStyle() != GLDrawStyle.INVALID) this.backgroundDrawStyle = def.backgroundDrawStyle();
        if(def.borderDrawStyle() != GLDrawStyle.INVALID) this.borderDrawStyle = def.borderDrawStyle();
        if(def.borderBevel() != null) this.bevelConfig = def.borderBevel();
        if(def.postProcesses() != null) this.postProcesses.addAll(def.postProcesses());
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
    public BevelConfig getBorderBevel() {
        return bevelConfig;
    }

    @Override
    public List<PostProcessShader> getPostProcesses() {
        return postProcesses;
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
