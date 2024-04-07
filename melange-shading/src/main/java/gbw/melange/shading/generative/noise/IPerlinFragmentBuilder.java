package gbw.melange.shading.generative.noise;

public interface IPerlinFragmentBuilder {

    IPerlinFragmentBuilder setSeed(int seed);
    IPerlinFragmentBuilder setOctaves(int amount);
    IPerlinFragmentBuilder setFrequency(double frequency);
    IPerlinFragmentBuilder setPersistence(double persistence);

    IPerlinNoiseShader build();

}
