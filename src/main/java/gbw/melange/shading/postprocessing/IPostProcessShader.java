package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.generative.ITexturedShader;

/**
 * Type alias for ShaderProgram for now
 */
public interface IPostProcessShader extends ITexturedShader {
    //TODO: Implement cache invalidation for post processing chains

    void invalidate();
}
