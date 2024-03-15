package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.errors.ShaderCompilationIssue;

/**
 * Represents a fully self-contained (besides from u_projTrans if applicable), uncompiled, shader program. <br/>
 * Initializing an instance of this through the Colors service will assure that it is managed and compiled automatically before rendering begins.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IWrappedShader extends Disposable {

    /**
     * Bind a resource to the shader - texture, contants, anything - these will should be applied every render cycle using {@link IWrappedShader#applyBindings()}
     */
    void bindResource(ShaderResourceBinding binding);
    /**
     * An IWrappedShader can be supplied any amounts of {@link ShaderResourceBinding} to bind various resources and textures to the shader. <br/>
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
     * Before this method is invoked, {@link gbw.melange.shading.IWrappedShader#getProgram()} will return null.
     *
     * @throws gbw.melange.shading.errors.ShaderCompilationIssue if any.
     */
    void compile() throws ShaderCompilationIssue;

    /**
     * Deep-copies (I think) a given shader.
     */
    IWrappedShader copy();
    IWrappedShader copyAs(String newLocalName);

    /**
     * Whether this shader is modified during its lifespan or not. If not, it will be rendered to a texture, which is drawn instead.
     * The texture itself will be stored on disk in assets/system/generated and used from there.
     */
    boolean isStatic();

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
}
