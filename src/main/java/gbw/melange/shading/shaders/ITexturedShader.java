package gbw.melange.shading.shaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.ShaderResourceBinding;

public interface ITexturedShader extends IWrappedShader<ITexturedShader> {

    /**
     * Set what texture to bind on render. This texture will also become managed and disposed when this shader is disposed of.
     * If you don't intend for that, define a "blind" binding instead using {@link IWrappedShader#bindResource(ShaderResourceBinding, Disposable...)}
     * without declaring its disposables at your own risk.
     * @param glslName The name of the uniform field of the texture in the underlying fragment shader
     */
    void setTexture(Texture texture, String glslName);
    Texture getTexture();


}
