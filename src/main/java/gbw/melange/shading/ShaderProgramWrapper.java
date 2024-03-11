package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.errors.ShaderCompilationIssue;
import gbw.melange.core.MelangeApplication;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Move all Shader initialization to dedicated Shader pipeline
 */
public class ShaderProgramWrapper {
    private static final Logger log = LoggerFactory.getLogger(ShaderProgramWrapper.class);

    private static ShaderProgramWrapper DEFAULT = null;
    private static ShaderProgramWrapper NONE = null;
    private static ShaderProgramWrapper TEXTURE = null;

    public static ShaderProgramWrapper mlg_texture(){
        if(TEXTURE == null){
            TEXTURE = new ShaderProgramWrapper("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, FragmentShader.TEXTURE);
        }
        return TEXTURE;
    }

    public static ShaderProgramWrapper mlg_default(){
        //Managed instance pattern used assure that the isCompiled check happens when LWJGL has initialized.
        if(DEFAULT == null){
            DEFAULT = new ShaderProgramWrapper("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT);
        }
        return DEFAULT;
    }
    public static ShaderProgramWrapper mlg_none(){
        if(NONE == null){
            NONE = new ShaderProgramWrapper("MELANGE_NONE_SHADER", VertexShader.NONE, FragmentShader.TRANSPARENT);
        }
        return NONE;
    }
    static { //TODO: Find out why Java is like this.
        mlg_texture();
        mlg_none();
        mlg_default();
    }

    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private ShaderProgram program;
    private final String localName;
    public ShaderProgramWrapper(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        this.localName = localName;
        ManagedShaderPipeline.add(this);
    }

    public void compile() throws ShaderCompilationIssue {
        this.program = new ShaderProgram(vertexShader.code(), fragmentShader.code());
        if(!program.isCompiled()){ //According to a certain source, compilation is synchronous on instantiation.
            throw new ShaderCompilationIssue(
            this + " failed to compile =================================\n" +
                program.getLog() + "\n" +
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n"
            );
        }
    }

    public ShaderProgram getProgram(){
        return program;
    }

    public String shortName(){
        return localName;
    }

    @Override
    public String toString(){
        return "Wrapped ShaderProgram: " + localName + " vertex shader: " + vertexShader.localName() + " fragment shader: " + fragmentShader.name();
    }
}
