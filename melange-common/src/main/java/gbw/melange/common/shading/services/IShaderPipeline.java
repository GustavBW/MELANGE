package gbw.melange.common.shading.services;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.errors.ShaderCompilationIssue;

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
     * @throws gbw.melange.common.shading.errors.ShaderCompilationIssue if any.
     */
    void compileAndCache() throws ShaderCompilationIssue, IOException;

    /**
     * <p>registerForCompilation.</p>
     *
     * @param shader a {@link IManagedShader} object
     */
    void registerForCompilation(IManagedShader<?> shader);

    /**
     * Clears all content in the dedicated output folder.
     * Shouldn't necessarily break anything, but is essentially a manual cache invalidation method.
     */
    void clearCache();

}
