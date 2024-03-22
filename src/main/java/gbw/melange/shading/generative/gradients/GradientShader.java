package gbw.melange.shading.generative.gradients;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.List;

public class GradientShader extends ManagedShader<IGradientShader> implements IGradientShader {
    public GradientShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    private double rotationRAD = 0;
    @Override
    public void setRotation(double deg) {
        this.rotationRAD = Math.toRadians(deg);
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniformf(GradientShaderAttr.ROTATION.glValue, (float) rotationRAD);
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected IGradientShader copyChild() {
        return new GradientShader(super.getLocalName(), getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected IGradientShader copyChildAs(String newLocalName) {
        return new GradientShader(newLocalName, getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected void disposeChildSpecificResources() {}
}