package gbw.melange.shading.generative.gradients;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.shading.generative.gradients.IGradientShader;
import gbw.melange.common.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.GenerativeShader;
import gbw.melange.common.shading.components.IFragmentShader;
import gbw.melange.common.shading.components.IVertexShader;

public class GradientShader extends GenerativeShader<IGradientShader> implements IGradientShader {
    public GradientShader(String localName, IVertexShader vertex, IFragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {
    }

    @Override
    protected boolean hasChildPropertiesChanged() {
        return false;
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected IGradientShader copyChild() {
        return new GradientShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

    @Override
    protected IGradientShader copyChildAs(String newLocalName) {
        return new GradientShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected void disposeChildSpecificResources() {}
}
