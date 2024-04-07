package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.IManagedShader;

/**
 * A specific style of shader which has at least 1 input texture
 */
public interface IPostProcessShader extends IManagedShader<IPostProcessShader> {
    void setInputTexture(Texture texture);
    Texture getTexture();
}
