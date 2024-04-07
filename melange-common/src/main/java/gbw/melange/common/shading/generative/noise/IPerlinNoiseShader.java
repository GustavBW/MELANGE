package gbw.melange.common.shading.generative.noise;

import gbw.melange.shading.IManagedShader;
import gbw.melange.common.shading.generative.IGenerativeShader;

public interface IPerlinNoiseShader extends IGenerativeShader<IPerlinNoiseShader> {

    void setOctaves(int amount);
    void setFrequency(double frequency);
    void setPersistence(double persistence);


}
