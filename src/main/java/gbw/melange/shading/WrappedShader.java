package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * //TODO: Move all Shader initialization to dedicated Shader pipeline
 * //TODO: Allow the wrapper to hold texture references and add a ".prep()" step to allow a wrapped shader to bind own resources
 */
public class WrappedShader implements IWrappedShader {
    private static final Logger log = LoggerFactory.getLogger(WrappedShader.class);

    public static WrappedShader DEFAULT = new WrappedShader("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT);
    public static WrappedShader NONE = new WrappedShader("MELANGE_NONE_SHADER", VertexShader.NONE, FragmentShader.TRANSPARENT);
    public static WrappedShader TEXTURE = new WrappedShader("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, FragmentShader.TEXTURE);

    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private ShaderProgram program;
    private final String localName;
    private Consumer<ShaderProgram> bindResources = unused -> {};
    private static final Consumer<ShaderProgram> NOOP_CONSUMER = unused -> {};
    public WrappedShader(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this(localName, vertexShader, fragmentShader, NOOP_CONSUMER);
    }
    public WrappedShader(String localName, VertexShader vertex, FragmentShader fragment, Consumer<ShaderProgram> bindResources){
        this.vertexShader = vertex;
        this.fragmentShader = fragment;
        this.localName = localName;
        this.bindResources = bindResources;
    }

    @Override
    public void bindResources() {
        bindResources.accept(program);
    }

    public void compile() throws ShaderCompilationIssue {
        this.program = new ShaderProgram(vertexShader.code(), fragmentShader.code());
        if(!program.isCompiled()){ //According to a certain source, compilation is synchronous on instantiation.
            throw new ShaderCompilationIssue(
            this + " failed to compile =================================\n" +
                program.getLog() + "\n" + //The log is a massive text dump
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
        return "Wrapped ShaderProgram: \"" + localName + "\", vertex shader: \"" + vertexShader.localName() + "\", fragment shader: \"" + fragmentShader.name() +"\"";
    }

    @Override
    public void dispose() {
        program.dispose();
    }
}
