package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.core.MelangeApplication;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.VertexShader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private final ShaderProgram program;
    private final String localName;
    public ShaderProgramWrapper(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        this.program = new ShaderProgram(vertexShader.code(), fragmentShader.code());
        this.localName = localName;
        if(!program.isCompiled()){ //According to a certain source, compilation is synchronous on instantiation.
            log.warn(this + " failed to compile");
            log.info(program.getLog());
            log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
    }

    public ShaderProgram getProgram(){
        return program;
    }

    @Override
    public String toString(){
        return "Wrapped ShaderProgram: " + localName + " vertex shader: " + vertexShader.localName() + " fragment shader: " + fragmentShader.name();
    }
}
