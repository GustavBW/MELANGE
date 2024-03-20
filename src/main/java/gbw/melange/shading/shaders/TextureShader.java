package gbw.melange.shading.shaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.shaders.partial.FragmentShader;
import gbw.melange.shading.shaders.partial.VertexShader;

import java.util.ArrayList;
import java.util.List;

public class TextureShader extends WrappedShader<ITexturedShader> implements ITexturedShader {
    public static ITexturedShader TEXTURE = new TextureShader("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, FragmentShader.TEXTURE, true, new ArrayList<>());
    public TextureShader(String localName, VertexShader vertex, FragmentShader fragment){
        this(localName, vertex, fragment, true, new ArrayList<>());
    }
    public TextureShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    private Texture textureToBind;
    private String glslName = GLShaderAttr.TEXTURE.glValue();

    @Override
    public void setTexture(Texture texture, String glslName) {
        if(this.textureToBind != null){
            textureToBind.dispose();
        }
        this.textureToBind = texture;
        this.glslName = glslName;
    }
    @Override
    public Texture getTexture() {
        return this.textureToBind;
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {
        int index = super.getNextBindingIndex();

        textureToBind.bind(index);
        program.setUniformi(glslName, index);
    }

    @Override
    protected void disposeChildSpecificResources() {
        textureToBind.dispose();
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.PURE_SAMPLER;
    }

    @Override
    protected ITexturedShader copyChild() {
        return new TextureShader(super.shortName(), getVertex(), getFragment(), isStatic(), super.bindings);
    }
    @Override
    protected ITexturedShader copyChildAs(String newLocalName) {
        return new TextureShader(newLocalName, getVertex(), getFragment(), isStatic(), super.bindings);
    }
}
