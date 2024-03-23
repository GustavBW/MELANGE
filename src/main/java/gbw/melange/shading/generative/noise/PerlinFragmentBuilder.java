package gbw.melange.shading.generative.noise;

import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import gbw.melange.shading.services.IShaderPipeline;

import java.util.ArrayList;

public class PerlinFragmentBuilder implements IPerlinFragmentBuilder{
    private int octaves = 3;
    private int seed = 1;
    private double frequency = 5;
    private double persistence = .1;
    private final String localName;
    private final IShaderPipeline pipeline;
    public PerlinFragmentBuilder(String localName, IShaderPipeline pipeline){
        this.localName = localName;
        this.pipeline = pipeline;
    }

    private String generateOpeningStatement(){
        return """
        #ifdef GL_ES
            precisioun mediump float;
        #endif
        varying vec2 v_texCoords;
        """ +
        "const int seed = " + seed + ";\n" +
        "uniform int " + PerlinShaderAttr.OCTAVES + ";\n" +
        "uniform float " + PerlinShaderAttr.FREQUENCY + ";\n" +
        "uniform float " + PerlinShaderAttr.PERSISTENCE + ";\n";
    }

    private static final String pseudoRandFunc = """
    float rand(vec2 co) {
        return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
    }
    """;

    private static final String noiseGeneratorFunc = """
    float noise(vec2 pos) {
        vec2 ip = floor(pos);
        vec2 u = fract(pos);
        u = u*u*(3.0-2.0*u);
            
        float res = mix(
            mix(rand(ip), rand(ip + vec2(1.0, 0.0)), u.x),
            mix(rand(ip + vec2(0.0, 1.0)), rand(ip + vec2(1.0, 1.0)), u.x), u.y);
        return res*res;
    }
    """;

    private static final String perlinEntryPoint = """
    float perlinNoise(vec2 pos, float frequency, int octaves, float persistence, int seed) {
        float total = 0.0;
        float amplitude = 1.0;
        float maxValue = 0.0;  // Used for normalizing result to 0.0 - 1.0
        pos *= frequency;

        for(int i = 0; i < octaves; i++) {
            total += noise(pos) * amplitude;
           
            maxValue += amplitude;
           
            amplitude *= persistence;
            pos *= 2.0;
        }

        return total/maxValue;
    }
    """;

    private static final String mainLoop =
     "void main() {\n" +
        "\tfloat value = perlinNoise(v_texCoords, " + PerlinShaderAttr.FREQUENCY + ", " + PerlinShaderAttr.OCTAVES + ", " + PerlinShaderAttr.PERSISTENCE + ", seed);\n" +
        "\tgl_FragColor = vec4(vec3(value), 1);\n" +
    "}\n";


    @Override
    public IPerlinNoiseShader build() {

        String sb = generateOpeningStatement() +
                pseudoRandFunc +
                noiseGeneratorFunc +
                perlinEntryPoint +
                mainLoop;

        FragmentShader fragment = new FragmentShader(localName, sb, ShaderClassification.COMPLEX);
        IPerlinNoiseShader shader = new PerlinNoiseShader(localName, VertexShader.DEFAULT, fragment, true, new ArrayList<>());
        if(pipeline != null){
            pipeline.registerForCompilation(shader);
        }
        shader.setFrequency(frequency);
        shader.setOctaves(octaves);
        shader.setPersistence(persistence);

        return shader;
    }

    @Override
    public IPerlinFragmentBuilder setSeed(int seed) {
        this.seed = seed;
        return this;
    }
    @Override
    public IPerlinFragmentBuilder setOctaves(int amount) {
        this.octaves = amount;
        return this;
    }
    @Override
    public IPerlinFragmentBuilder setFrequency(double frequency) {
        this.frequency = frequency;
        return this;
    }
    @Override
    public IPerlinFragmentBuilder setPersistence(double persistence) {
        this.persistence = persistence;
        return this;
    }

}
