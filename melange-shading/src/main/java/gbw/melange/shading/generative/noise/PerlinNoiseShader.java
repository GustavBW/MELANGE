package gbw.melange.shading.generative.noise;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.shading.generative.noise.IPerlinNoiseShader;
import gbw.melange.common.shading.generative.noise.PerlinShaderAttr;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.GenerativeShader;
import gbw.melange.shading.components.FragmentShader;
import gbw.melange.shading.components.VertexShader;

public class PerlinNoiseShader extends GenerativeShader<IPerlinNoiseShader> implements IPerlinNoiseShader {
    public PerlinNoiseShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    private int octaves = 1;
    private double frequency = 1;
    private double persistence = 1;
    private boolean hasChanged = true;
    @Override
    public void setOctaves(int amount) {
        hasChanged = amount != octaves;
        this.octaves = amount;
    }
    @Override
    public void setFrequency(double frequency) {
        hasChanged = frequency != this.frequency;
        this.frequency = frequency;
    }
    @Override
    public void setPersistence(double persistence) {
        hasChanged = persistence != this.persistence;
        this.persistence = persistence;
    }


    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniformf(PerlinShaderAttr.FREQUENCY.glValue, (float) frequency);
        program.setUniformi(PerlinShaderAttr.OCTAVES.glValue, octaves);
        program.setUniformf(PerlinShaderAttr.PERSISTENCE.glValue, (float) persistence);
        hasChanged = false;
    }

    @Override
    protected boolean hasChildPropertiesChanged() {
        return hasChanged;
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected IPerlinNoiseShader copyChild() {
        return new PerlinNoiseShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

    @Override
    protected IPerlinNoiseShader copyChildAs(String newLocalName) {
        return new PerlinNoiseShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected void disposeChildSpecificResources() {}


}
