package gbw.melange.shading.generative;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.components.FragmentShader;
import gbw.melange.shading.components.VertexShader;

public class BlindShader extends GenerativeShader<BlindShader> {
    public BlindShader(String localName, VertexShader vertex, FragmentShader fragment){
        this(localName, vertex, fragment, true);
    }
    public BlindShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic) {
        this(localName, vertex, fragment, ShaderClassification.COMPLEX, isStatic);
    }
    public BlindShader(String localName, VertexShader vertex, FragmentShader fragment, ShaderClassification classification, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
        this.classification = classification;
    }

    private ShaderClassification classification;
    @Override
    protected void applyChildBindings(ShaderProgram program) {}

    @Override
    protected boolean hasChildPropertiesChanged() {
        return true;
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return classification;
    }

    @Override
    protected BlindShader copyChild() {
        return new BlindShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

    @Override
    protected BlindShader copyChildAs(String newLocalName) {
        return new BlindShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected void disposeChildSpecificResources() {}
}
