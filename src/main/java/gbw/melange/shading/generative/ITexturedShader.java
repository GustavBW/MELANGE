package gbw.melange.shading.generative;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.ShaderResourceBinding;

/**
 * Any shader with a texture sampling input
 */
public interface ITexturedShader extends IGenerativeShader<ITexturedShader> {

    /**
     * Set what texture to bind on render. This texture will also become managed and disposed when this shader is disposed of.
     * @param glslName The name of the uniform field of the texture in the underlying fragment shader
     */
    void setTexture(Texture texture, String glslName);
    Texture getTexture();


}
