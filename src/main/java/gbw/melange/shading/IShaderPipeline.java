package gbw.melange.shading;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.errors.ShaderCompilationIssue;

import java.io.IOException;

/**
 * <p>IShaderPipeline interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IShaderPipeline extends Disposable {
    /**
     * Compiles all registered programs.
     * Also stores all static shaders on disk as textures and replace the actual program with one sampling that if caching is enabled.
     * @throws gbw.melange.shading.errors.ShaderCompilationIssue if any.
     */
    void compileAndCache() throws ShaderCompilationIssue, IOException;

    /**
     * <p>registerForCompilation.</p>
     *
     * @param shader a {@link gbw.melange.shading.IWrappedShader} object
     */
    void registerForCompilation(IWrappedShader shader);

    /**
     * Whether subsequent compile steps should attempt to statically cache shaders as textures to be drawn instead.
     * This should increase render performance drastically.
     */
    void useCaching(boolean yesNo);

}
