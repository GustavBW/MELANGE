package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.generative.TextureShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.List;

public abstract class PostProcessShader extends TextureShader implements IPostProcessShader {

    public PostProcessShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    @Override
    public void setTexture(Texture texture, String glslName) {
        //completely ignores the custom glslName as the post processing shaders require a standardized format.
        super.setTexture(texture, GLShaderAttr.TEXTURE.glValue());
    }

    @Override
    public void invalidate(){
        throw new RuntimeException("Implement this first, how 'bout that");
    }

}
