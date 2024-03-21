package gbw.melange.shading.services;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.generative.IWrappedShader;
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
     * @param shader a {@link IWrappedShader} object
     */
    void registerForCompilation(IWrappedShader<?> shader);

    /**
     * Whether subsequent compile steps should attempt to statically cache shaders as textures to be drawn instead.
     * This should increase render performance drastically.
     */
    void useCaching(boolean yesNo);

    /**
     * Clears all content in the dedicated output folder.
     * Shouldn't necessarily break anything, but is essentially a manual cache invalidation method.
     */
    void clearCache();

}
