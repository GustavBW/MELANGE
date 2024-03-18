package gbw.melange.shading.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.events.observability.IPrestineBlockingObservable;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A self contained ShaderProgram, allowing for explicit compilation and error detection.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class WrappedShader implements IWrappedShader {
    private static final Logger log = LogManager.getLogger();

    /** Constant <code>DEFAULT</code> */
    public static WrappedShader DEFAULT = new WrappedShader("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT);

    /** Constant <code>TEXTURE</code> */
    public static WrappedShader TEXTURE = new WrappedShader("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, FragmentShader.TEXTURE);

    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private final String localName; //Debugging
    /**
     * Whether parameters of this shader changes during runtime (not static) or not (is static)
     */
    private boolean isStatic;
    private final List<ShaderResourceBinding> bindings;
    private final List<Disposable> combinedDisposables = new ArrayList<>();
    private ShaderProgram program;
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
        this.bindings = bindings;
    }

    /** {@inheritDoc} */
    @Override
    public void applyBindings() {
        for (int i = 0; i < bindings.size(); i++){
            ShaderResourceBinding binding = bindings.get(i);
            //Swap to primes for complete solution. This only allows 100 unique bindings per shader, which should be enough, but you never know
            binding.bind((i + 1), program);
        }
    }

    @Override
    public ShaderClassification getClassification() {
        return fragmentShader.getClassification();
    }
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
    @Override
    public IWrappedShader copy(){
        return new WrappedShader(localName, vertexShader, fragmentShader, isStatic, bindings);
    }
    @Override
    public IWrappedShader copyAs(String newLocalName){
        return new WrappedShader(newLocalName, vertexShader, fragmentShader, isStatic, bindings);
    }


    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Wrapped ShaderProgram: \"" + localName + "\", vertex shader: \"" + vertexShader.localName() + "\", fragment shader: \"" + fragmentShader.name() +"\"";
    }
    /** {@inheritDoc} */
    @Override
    public void dispose() {
        combinedDisposables.forEach(Disposable::dispose);
        program.dispose();
    }

    /**
     * @return the old program
     */
    void replaceProgram(VertexShader vertex, FragmentShader frag){
        this.program.dispose();
        this.program = new ShaderProgram(vertex.code(), frag.code());
    }
    void clearBindings(){
        log.trace("Clearing bindings " + bindings);
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
