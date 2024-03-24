package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.generative.ITexturedShader;

/**
 * A specific style of shader which has at least 1 input texture
 */
public interface IPostProcessShader extends IManagedShader<IPostProcessShader> {
    void setTexture(Texture texture);
    Texture getTexture();
}
