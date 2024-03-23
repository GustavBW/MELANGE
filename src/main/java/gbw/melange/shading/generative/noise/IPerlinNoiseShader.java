package gbw.melange.shading.generative.noise;

import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.generative.voronoi.IVoronoiShader;

public interface IPerlinNoiseShader extends IManagedShader<IPerlinNoiseShader> {

    void setOctaves(int amount);
    void setFrequency(double frequency);
    void setPersistence(double persistence);


}
