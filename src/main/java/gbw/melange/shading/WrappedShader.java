package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A self contained ShaderProgram, allowing for explicit compilation and error detection.
 *
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

    private static int nextInstanceId = 0;
    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private final String localName; //Debugging
    private final boolean isStatic;
    private List<ShaderResourceBinding> bindings;
    private ShaderProgram program;
    private final int instanceId;
    private boolean failedCompilation = false;
    /**
     * If this texture is rendered to disk for memory purposes, what resolution should it be stored as.
     */
    private int desiredResolution = 500;

    public WrappedShader(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this(localName, vertexShader, fragmentShader, true);
    }
    public WrappedShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic){
        this(localName, vertex, fragment, isStatic, new ArrayList<>());
    }

    public WrappedShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings){
        this.vertexShader = vertex;
        this.fragmentShader = fragment;
        this.localName = localName;
        this.isStatic = isStatic;
        this.instanceId = nextInstanceId++;
        this.bindings = bindings;
    }

    /** {@inheritDoc} */
    @Override
    public void applyBindings() {
        for (int i = 0; i < bindings.size(); i++){
            ShaderResourceBinding binding = bindings.get(i);
            //Swap to primes for complete solution. This only allows 100 unique bindings per shader, which should be enough, but you never know
            binding.bind(instanceId * 100 + i, program);
        }
    }

    @Override
    public void bindResource(ShaderResourceBinding binding){
        bindings.add(binding);
    }



    /** {@inheritDoc} */
    @Override
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
    @Override
    public IWrappedShader copy(){
        return new WrappedShader(localName, vertexShader, fragmentShader, isStatic, bindings);
    }
    @Override
    public IWrappedShader copyAs(String newLocalName){
        return new WrappedShader(newLocalName, vertexShader, fragmentShader, isStatic, bindings);
    }

    /** {@inheritDoc} */
    public ShaderProgram getProgram(){
        return program;
    }
    /** {@inheritDoc} */
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
    public void setResolution(int res) {
        this.desiredResolution = res;
    }
    /** {@inheritDoc} */
    @Override
    public int getResolution() {
        return desiredResolution;
    }
    /** {@inheritDoc} */
    @Override
    public boolean isStatic(){
        return isStatic;
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

    void replaceProgram(ShaderProgram program){
        this.program = program;
    }
    void changeBindings(List<ShaderResourceBinding> bindings){
        this.bindings = bindings;
    }
    int getInstanceId(){
        return instanceId;
    }
}
