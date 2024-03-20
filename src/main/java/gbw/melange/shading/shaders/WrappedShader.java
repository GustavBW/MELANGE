package gbw.melange.shading.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.shaders.partial.FragmentShader;
import gbw.melange.shading.shaders.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A self contained ShaderProgram, allowing for explicit compilation and error detection.
 *
 * @param <T> Type of the specific child instance;
 */
public abstract class WrappedShader<T extends IWrappedShader<T>> implements IWrappedShader<T> {
    private static final Logger log = LogManager.getLogger();

    public static WrappedShader<?> DEFAULT = new BlindShader("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT, false, new ArrayList<>());


    protected final List<Disposable> combinedDisposables = new ArrayList<>();
    protected final List<ShaderResourceBinding> bindings;
    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private final String localName; //Debugging
    /**
     * Whether parameters of this shader changes during runtime (not static) or not (is static)
     */
    private boolean isStatic;
    private ShaderProgram program;
    private boolean failedCompilation = false;

    /**
     * If this texture is rendered to disk for memory purposes, what resolution should it be stored as.
     * E.g. 500x500
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
        this.bindings = bindings;
    }


    private int nextBindingIndex = 0;
    protected int getNextBindingIndex(){
        return nextBindingIndex++;
    }

    /** {@inheritDoc} */
    @Override
    public void applyBindings() {
        applyChildBindings(program);
        for (var binding : bindings){
            binding.bind(getNextBindingIndex(), program);
        }
        nextBindingIndex = 0;
    }
    protected abstract void applyChildBindings(ShaderProgram program);

    @Override
    public ShaderClassification getClassification() {
        if(getChildClassification().abstractValRep > fragmentShader.getClassification().abstractValRep){
            return getChildClassification();
        }
        return fragmentShader.getClassification();
    }
    protected abstract ShaderClassification getChildClassification();
    @Override
    public void bindResource(ShaderResourceBinding binding, Disposable... disposables){
        bindings.add(binding);

        if(disposables == null) return;
        this.combinedDisposables.addAll(List.of(disposables));
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

    public T copy(){
        T child = copyChild();
        try{
            child.compile();
        }catch (Exception partiallyIgnored){
            log.warn("Compiling a new copy of " + child.shortName() + " failed: " + partiallyIgnored.getMessage());
        }
        return child;
    }
    protected abstract T copyChild();
    public T copyAs(String newLocalName){
        T child = copyChildAs(newLocalName);
        try{
            child.compile();
        }catch (Exception partiallyIgnored){
            log.warn("Compiling a new copy of " + child.shortName() + " as: " + newLocalName + " failed: " + partiallyIgnored.getMessage());
        }
        return child;
    }
    protected abstract T copyChildAs(String newLocalName);


    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Wrapped ShaderProgram: \"" + localName + "\", vertex shader: \"" + vertexShader.localName() + "\", fragment shader: \"" + fragmentShader.name() +"\"";
    }
    /** {@inheritDoc} */
    @Override
    public void dispose() {
        disposeChildSpecificResources();
        combinedDisposables.forEach(Disposable::dispose);
        program.dispose();
    }
    protected abstract void disposeChildSpecificResources();

    /**
     * @return the old program
     */
    public void replaceProgram(VertexShader vertex, FragmentShader frag){
        this.program.dispose();
        this.program = new ShaderProgram(vertex.code(), frag.code());
    }
    public void clearBindings(){
        log.debug("Clearing bindings " + bindings + " for " + this.shortName());
        bindings.clear();
        combinedDisposables.forEach(Disposable::dispose);
        combinedDisposables.clear();
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

    @Override
    public FragmentShader getFragment() {
        return fragmentShader;
    }

    @Override
    public VertexShader getVertex() {
        return vertexShader;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isStatic(){
        return isStatic && fragmentShader.isStatic();
    }

    @Override
    public void setStatic(boolean yesNo) {
        this.isStatic = yesNo;
    }
}
