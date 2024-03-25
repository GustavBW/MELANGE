package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.generative.TextureShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.List;

public abstract class PostProcessShader extends ManagedShader<IPostProcessShader> implements IPostProcessShader {

    public PostProcessShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    private Texture latest = null;

    @Override
    public void setInputTexture(Texture texture) {
        this.latest = texture;
    }
    @Override
    public Texture getTexture(){
        return latest;
    }
    @Override
    public void applyChildBindings(ShaderProgram program){
        final int index = super.getNextBindingIndex();

        latest.bind(index);
        program.setUniformi(GLShaderAttr.TEXTURE.glValue(), index);
    }
    @Override
    public void disposeChildSpecificResources(){
        if(latest != null){
            latest.dispose();
        }
    }
}
