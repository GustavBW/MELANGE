package gbw.melange.shading;

import gbw.melange.shading.errors.ShaderCompilationIssue;

public interface IShaderPipeline {
    void compileAll() throws ShaderCompilationIssue;
    void registerForCompilation(IWrappedShader shader);

}
