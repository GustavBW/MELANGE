package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.errors.ShaderCompilationIssue;

/**
 * Represents a fully self-contained (besides from u_projTrans if applicable), uncompiled, shader program. <br/>
 * Initializing an instance of this through the Colors service will assure that it is managed and compiled automatically.
 */
public interface IWrappedShader extends Disposable {
    /**
     * An IWrappedShader be supplied a {@code Consumer<ShaderProgram>} to bind various resources and textures to the shader. <br/>
     * This method should be called before using the shader for rendering.
     */
    void bindResources();

    /**
     * Compiles the program and throws an error immediately if the compilation process isn't successful.
     * Before this method is invoked, {@link IWrappedShader#getProgram()} will return null.
     */
    void compile() throws ShaderCompilationIssue;

    /**
     * @return null if not compiled, else a ShaderProgram
     */
    ShaderProgram getProgram();
    String shortName();
}
