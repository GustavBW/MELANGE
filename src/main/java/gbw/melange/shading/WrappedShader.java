package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author GustavBW
 * @version $Id: $Id
 */
public class WrappedShader implements IWrappedShader {
    private static final Logger log = LoggerFactory.getLogger(WrappedShader.class);

    /** Constant <code>DEFAULT</code> */
    public static WrappedShader DEFAULT = new WrappedShader("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT);
    /** Constant <code>NONE</code> */
    public static WrappedShader NONE = new WrappedShader("MELANGE_NONE_SHADER", VertexShader.NONE, FragmentShader.TRANSPARENT);
    /** Constant <code>TEXTURE</code> */
    public static WrappedShader TEXTURE = new WrappedShader("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, FragmentShader.TEXTURE);

    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private ShaderProgram program;
    private final String localName;
    private boolean failedCompilation = false;
    private Consumer<ShaderProgram> bindResources = unused -> {};
    private static final Consumer<ShaderProgram> NOOP_CONSUMER = unused -> {};
    /**
     * <p>Constructor for WrappedShader.</p>
     *
     * @param localName a {@link java.lang.String} object
     * @param vertexShader a {@link gbw.melange.shading.VertexShader} object
     * @param fragmentShader a {@link gbw.melange.shading.FragmentShader} object
     */
    public WrappedShader(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this(localName, vertexShader, fragmentShader, NOOP_CONSUMER);
    }
    /**
     * <p>Constructor for WrappedShader.</p>
     *
     * @param localName a {@link java.lang.String} object
     * @param vertex a {@link gbw.melange.shading.VertexShader} object
     * @param fragment a {@link gbw.melange.shading.FragmentShader} object
     * @param bindResources a {@link java.util.function.Consumer} object
     */
    public WrappedShader(String localName, VertexShader vertex, FragmentShader fragment, Consumer<ShaderProgram> bindResources){
        this.vertexShader = vertex;
        this.fragmentShader = fragment;
        this.localName = localName;
        this.bindResources = bindResources;
    }

    /** {@inheritDoc} */
    @Override
    public void bindResources() {
        bindResources.accept(program);
    }

    /**
     * <p>compile.</p>
     *
     * @throws gbw.melange.shading.errors.ShaderCompilationIssue if any.
     */
    public void compile() throws ShaderCompilationIssue {
        this.program = new ShaderProgram(vertexShader.code(), fragmentShader.code());
        failedCompilation = !program.isCompiled();
        if(failedCompilation){ //According to a certain source, compilation is synchronous on instantiation.
            throw new ShaderCompilationIssue(
            this + " failed to compile =================================\n" +
                program.getLog() + "\n" + //The log is a massive text dump
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n"
            );
        }
    }

    /**
     * <p>Getter for the field <code>program</code>.</p>
     *
     * @return a {@link com.badlogic.gdx.graphics.glutils.ShaderProgram} object
     */
    public ShaderProgram getProgram(){
        return program;
    }

    /**
     * <p>shortName.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String shortName(){
        return localName;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isReady() {
        return !failedCompilation && program != null;
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Wrapped ShaderProgram: \"" + localName + "\", vertex shader: \"" + vertexShader.localName() + "\", fragment shader: \"" + fragmentShader.name() +"\"";
    }

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        program.dispose();
    }
}
