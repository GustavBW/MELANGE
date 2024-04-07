package gbw.melange.common.shading.generative;

import com.badlogic.gdx.graphics.Texture;

/**
 * Any shader with a texture sampling input
 */
public interface ITexturedShader extends IGenerativeShader<ITexturedShader> {

    /**
     * Set what texture to bind on render. This texture will not become managed and disposed when this shader is disposed of.
     * @param glslName The name of the uniform field of the texture in the underlying fragment shader
     */
    void setTexture(Texture texture, String glslName);
    Texture getTexture();


}
