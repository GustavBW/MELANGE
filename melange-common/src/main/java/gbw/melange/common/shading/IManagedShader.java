package gbw.melange.common.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.shading.components.IFragmentShader;
import gbw.melange.common.shading.components.IVertexShader;
import gbw.melange.common.shading.constants.ShaderClassification;
import gbw.melange.common.shading.errors.ShaderCompilationIssue;
import gbw.melange.common.shading.services.IShaderPipeline;

/**
 * Represents a fully self-contained (besides from u_projTrans if applicable), uncompiled, shader program. <br/>
 * Initializing an instance of this through the Colors service will assure that it is managed and compiled automatically before rendering begins.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IManagedShader<T extends IManagedShader<T>> extends Disposable {

    /**
     * An IManagedShader can be supplied any amounts of {@link ShaderResourceBinding} to bind various resources and textures to the shader. <br/>
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
     * Before this method is invoked, {@link IManagedShader#getProgram()} will return null.
     */
    void compile() throws ShaderCompilationIssue;

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
     * Will return null until compiled. If a latest cache result (texture) is present, the program used to draw this cached
     * texture is returned. Else, the original shader program defined for this shader is.
     */
    ShaderProgram getProgram();
    /**
     * @return a name to help identify the shader
     */
    String getLocalName();

    /**
     * True, if the shader program exists and has compiled successfully.
     * @return a boolean
     */
    boolean isReady();

    /**
     * Set the resolution this shader should be stored at if managed and {@link IManagedShader#isStatic()} and thus written to disk.
     * If the shader has already been written to disk, the stored texture will be updated.
     */
    void setResolution(int res);

    /**
     * Get the resolution for storing this shader to disk
     */
    int getResolution();

    IFragmentShader getFragment();
    IVertexShader getVertex();
}
