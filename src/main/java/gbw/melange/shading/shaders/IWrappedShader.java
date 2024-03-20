package gbw.melange.shading.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.services.IShaderPipeline;
import gbw.melange.shading.shaders.partial.FragmentShader;
import gbw.melange.shading.shaders.partial.VertexShader;

/**
 * Represents a fully self-contained (besides from u_projTrans if applicable), uncompiled, shader program. <br/>
 * Initializing an instance of this through the Colors service will assure that it is managed and compiled automatically before rendering begins.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IWrappedShader<T extends IWrappedShader<T>> extends Disposable {

    /**
     * Bind a resource to the shader - texture, constants, anything - these will should be applied every render cycle using {@link IWrappedShader#applyBindings()}<br/>
     * Do however also provide a reference to all bound resources that should be disposed when this shader is disposed. <br/>
     * Duly note that OpenGL has limits to the amount of unique binding indexes for different purposes (textures for instance) possible, so you might want to batch things that require unique indexes and no indexes at all.
     * @param binding any function taking in the bind index and the program. Example: <br/>
     *
     * <pre>
     *      {@code
     *      shader.bindResource((int index, ShaderProgram program) -> {
     *              anything here
 *          }, resource0, resource1...)
 *          }
     *
     * </pre>
     */
    void bindResource(ShaderResourceBinding binding, Disposable... disposables);
    /**
     * An IWrappedShader can be supplied any amounts of {@link ShaderResourceBinding} to bind various resources and textures to the shader. <br/>
     * Do bind the shader program itself to the gl context ({@link ShaderProgram#bind()}) before invoking this method, or gl gets angry.
     * This method should be called before using the shader for rendering regardless.
     */
    void applyBindings();

    /**
     * The complexity classification for a given shader.
     * As of now, this but the classification of the FragmentShader.
     */
    ShaderClassification getClassification();

    /**
     * Compiles the program and throws an error immediately if the compilation process isn't successful.
     * Before this method is invoked, {@link IWrappedShader#getProgram()} will return null.
     */
    void compile() throws ShaderCompilationIssue;

    /**
     * Deep-copies (I think) a given shader.
     */
    T copy();
    T copyAs(String newLocalName);

    /**
     * Whether this shader is modified during its lifespan or not. If not, it is a candidate for cashing and might be rendered to a texture, which is drawn instead.
     * The texture itself will be stored on disk in assets/system/generated and used from there.
     */
    boolean isStatic();

    /**
     * Overrides any default value. If done before {@link IShaderPipeline#compileAndCache()} is called,
     * which generally happens as one of the last things during create, this will be successful.
     * If not, it will make no difference as of right now. //TODO: Implement cache invalidation for this case
     * @param yesNo true / false
     */
    void setStatic(boolean yesNo);

    /**
     * @return null if not compiled, else a ShaderProgram
     */
    ShaderProgram getProgram();
    /**
     * @return a name to help identify the shader
     */
    String shortName();

    /**
     * True, if the shader program exists and has compiled successfully.
     * @return a boolean
     */
    boolean isReady();

    /**
     * Set the resolution this shader should be stored at if managed and {@link IWrappedShader#isStatic()} and thus written to disk.
     * If the shader has already been written to disk, the stored texture will be updated.
     */
    void setResolution(int res);

    /**
     * Get the resolution for storing this shader to disk
     */
    int getResolution();

    FragmentShader getFragment();
    VertexShader getVertex();
}
