package gbw.melange.shading.generative;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.ArrayList;
import java.util.List;

public class BlindShader extends GenerativeShader {
    public BlindShader(String localName, VertexShader vertex, FragmentShader fragment){
        this(localName, vertex, fragment, true);
    }
    public BlindShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {}

    @Override
    protected boolean hasChildPropertiesChanged() {
        return true;
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected IManagedShader<?> copyChild() {
        return new BlindShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

    @Override
    protected IManagedShader<?> copyChildAs(String newLocalName) {
        return new BlindShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected void disposeChildSpecificResources() {}
}
