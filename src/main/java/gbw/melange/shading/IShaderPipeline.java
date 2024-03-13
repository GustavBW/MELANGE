package gbw.melange.shading;

import gbw.melange.shading.errors.ShaderCompilationIssue;

/**
 * <p>IShaderPipeline interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IShaderPipeline {
    /**
     * <p>compileAll.</p>
     *
     * @throws gbw.melange.shading.errors.ShaderCompilationIssue if any.
     */
    void compileAll() throws ShaderCompilationIssue;
    /**
     * <p>registerForCompilation.</p>
     *
     * @param shader a {@link gbw.melange.shading.IWrappedShader} object
     */
    void registerForCompilation(IWrappedShader shader);

}
