package gbw.melange.shading.generative;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.shading.generative.ITexturedShader;
import gbw.melange.common.shading.constants.GLShaderAttr;
import gbw.melange.common.shading.constants.ShaderClassification;
import gbw.melange.shading.components.IFragmentShader;
import gbw.melange.shading.components.VertexShader;

public class TextureShader extends GenerativeShader<ITexturedShader> implements ITexturedShader {
    public static ITexturedShader TEXTURE = new TextureShader("MELANGE_TEXTURE_SHADER", VertexShader.DEFAULT, IFragmentShader.TEXTURE, true);
    public TextureShader(String localName, VertexShader vertex, IFragmentShader fragment){
        this(localName, vertex, fragment, true);
    }
    public TextureShader(String localName, VertexShader vertex, IFragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    private Texture textureToBind;
    private boolean hasChanged = true;
    private String glslName = GLShaderAttr.TEXTURE.glValue();

    @Override
    public void setTexture(Texture texture, String glslName) {
        hasChanged = texture != textureToBind;
        this.textureToBind = texture;
        this.glslName = glslName;
    }

    @Override
    protected boolean hasChildPropertiesChanged() {
        return hasChanged;
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
        hasChanged = false;
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
        return new TextureShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }
    @Override
    protected ITexturedShader copyChildAs(String newLocalName) {
        return new TextureShader(newLocalName, getVertex(), getFragment(), isStatic());
    }
}
