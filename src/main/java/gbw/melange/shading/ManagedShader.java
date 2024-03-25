package gbw.melange.shading;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.generative.BlindShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A self contained ShaderProgram, allowing for explicit compilation and error detection.
 *
 * @param <T> Type of the specific child instance;
 */
public abstract class ManagedShader<T extends IManagedShader<T>> implements IManagedShader<T> {
    private static final Logger log = LogManager.getLogger();
    public static ManagedShader<?> DEFAULT = new BlindShader("MELANGE_DEFAULT_SHADER", VertexShader.DEFAULT, FragmentShader.DEFAULT, false);
    private final FragmentShader fragmentShader;
    private final VertexShader vertexShader;
    private final String localName; //Debugging
    /**
     * Whether parameters of this shader changes during runtime (not static) or not (is static)
     */
    private boolean isStatic;
    private ShaderProgram program;
    private ShaderProgram whenCachedProgram;
    private boolean failedCompilation = false, hasChanged = true;
    private Texture cachedResult = null;

    /**
     * If this texture is rendered to disk for memory purposes, what resolution should it be stored as.
     * E.g. 500x500
     */
    private int desiredResolution = 500;

    public ManagedShader(String localName, VertexShader vertexShader, FragmentShader fragmentShader) {
        this(localName, vertexShader, fragmentShader, true);
    }
    public ManagedShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic){
        this.localName = localName;
        this.vertexShader = vertex;
        this.fragmentShader = fragment;
        this.isStatic = isStatic;
    }

    private int nextBindingIndex = 0;
    protected int getNextBindingIndex(){
        return nextBindingIndex++;
    }

    /** {@inheritDoc} */
    @Override
    public void applyBindings() {
        //TODO: Find out why trying to save resources with this check causes immesurable damage.
        //if(!(hasChanged || hasChildPropertiesChanged())){
        //    return;
        //}

        if (cachedResult != null) {
            int index = getNextBindingIndex();
            cachedResult.bind(index);
            whenCachedProgram.setUniformi(GLShaderAttr.TEXTURE.glValue(), index);
        } else {
            applyChildBindings(program);
        }
        hasChanged = false;
        nextBindingIndex = 0;
    }
    protected abstract void applyChildBindings(ShaderProgram program);
    protected abstract boolean hasChildPropertiesChanged();

    /**
     * Used for determining whether to cache this shader or not.
     */
    @Override
    public ShaderClassification getClassification() {
        ShaderClassification childClassification = getChildClassification();
        if(childClassification.abstractValRep > fragmentShader.getClassification().abstractValRep){
            return childClassification;
        }
        return fragmentShader.getClassification();
    }
    protected abstract ShaderClassification getChildClassification();

    /** {@inheritDoc} */
    @Override
    public void compile() throws ShaderCompilationIssue {
        this.program = new ShaderProgram(vertexShader.code(), fragmentShader.code());
        failedCompilation = !program.isCompiled();
        if(failedCompilation){ //According to a certain source, compilation is synchronous on instantiation.
            throw new ShaderCompilationIssue(
            this + " failed to compile =================================\n" +
                program.getLog() + "\n" + //The log is a massive text dump
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                "VERTEXT: \n\n" + vertexShader.code() + "\n\n FRAGMENT \n\n" + fragmentShader.code()
            );
        }
    }


    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Wrapped ShaderProgram: \"" + localName + "\", vertex shader: \"" + vertexShader.localName() + "\", fragment shader: \"" + fragmentShader.name() +"\"";
    }

    public void setCachedTextureProgram(VertexShader vertex, FragmentShader frag){
        ShaderProgram cachedTextureProgram = new ShaderProgram(vertex.code(), frag.code());
        if(!cachedTextureProgram.isCompiled()){
            log.warn("Unable to swap to cached texture program because compilation failed");
            log.debug(cachedTextureProgram.getLog());
            return;
        }
        this.whenCachedProgram = cachedTextureProgram;
    }

    /**
     * Setting this to null effectively works as cache invalidation
     */
    public void setCachedTexture(Texture texture){
        if(texture != null && (whenCachedProgram == null || !whenCachedProgram.isCompiled())){
            log.warn("Tried to set cached texture when either the whenCachedProgram is null or didn't compile. The attempt has been ignored to prevent further issues.");
            return;
        }
        hasChanged = texture != cachedResult;
        this.cachedResult = texture;
    }

    /** {@inheritDoc} */
    @Override
    public ShaderProgram getProgram(){
        return cachedResult == null ? program : whenCachedProgram;
    }
    /** {@inheritDoc} */
    @Override
    public String getLocalName(){
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

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        disposeChildSpecificResources();
        program.dispose();
        if(whenCachedProgram != null){
            whenCachedProgram.dispose();
        }
        if(cachedResult != null){
            cachedResult.dispose();
        }
    }
    protected abstract void disposeChildSpecificResources();
}
