package gbw.melange.shading.generative.noise;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.checker.CheckerShader;
import gbw.melange.shading.generative.checker.CheckerShaderAttr;
import gbw.melange.shading.generative.checker.ICheckerShader;
import gbw.melange.shading.generative.gradients.GradientShaderAttr;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.List;

public class PerlinNoiseShader extends ManagedShader<IPerlinNoiseShader> implements IPerlinNoiseShader {
    public PerlinNoiseShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    private int octaves = 1;
    private double frequency = 1;
    private double persistence = 1;
    @Override
    public void setOctaves(int amount) {
        this.octaves = amount;
    }
    @Override
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    @Override
    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }


    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniformf(PerlinShaderAttr.FREQUENCY.glValue, (float) frequency);
        program.setUniformi(PerlinShaderAttr.OCTAVES.glValue, octaves);
        program.setUniformf(PerlinShaderAttr.PERSISTENCE.glValue, (float) persistence);
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected IPerlinNoiseShader copyChild() {
        return new PerlinNoiseShader(super.getLocalName(), getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected IPerlinNoiseShader copyChildAs(String newLocalName) {
        return new PerlinNoiseShader(newLocalName, getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected void disposeChildSpecificResources() {}


}
