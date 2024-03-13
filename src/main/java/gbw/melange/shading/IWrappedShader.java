package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.errors.ShaderCompilationIssue;

/**
 * Represents a fully self-contained (besides from u_projTrans if applicable), uncompiled, shader program. <br/>
 * Initializing an instance of this through the Colors service will assure that it is managed and compiled automatically.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IWrappedShader extends Disposable {
    /**
     * An IWrappedShader be supplied a {@code Consumer<ShaderProgram>} to bind various resources and textures to the shader. <br/>
     * This method should be called before using the shader for rendering.
     */
    void bindResources();

    /**
     * Compiles the program and throws an error immediately if the compilation process isn't successful.
     * Before this method is invoked, {@link gbw.melange.shading.IWrappedShader#getProgram()} will return null.
     *
     * @throws gbw.melange.shading.errors.ShaderCompilationIssue if any.
     */
    void compile() throws ShaderCompilationIssue;

    /**
     * <p>getProgram.</p>
     *
     * @return null if not compiled, else a ShaderProgram
     */
    ShaderProgram getProgram();
    /**
     * <p>shortName.</p>
     *
     * @return a {@link java.lang.String} object
     */
    String shortName();

    /**
     * True, if the shader program exists and has compiled successfully.
     *
     * @return a boolean
     */
    boolean isReady();
}
