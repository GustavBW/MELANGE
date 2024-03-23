package gbw.melange.shading.generative.gradients;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.shading.constants.InterpolationType;
import gbw.melange.shading.services.IShaderPipeline;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GustavBW
 */
public class GradientFragmentBuilder implements IGradientBuilder {
    private static final Logger log = LogManager.getLogger();
    private double rotationDeg = 0;
    private InterpolationType interpolationType = InterpolationType.HERMIT;
    private final List<Color> colors = new ArrayList<>();
    private final List<Double> positions = new ArrayList<>();
    private final String localName;
    private final IShaderPipeline pipeline;


    public GradientFragmentBuilder(String localName){
        this(localName, null);
    }

    public GradientFragmentBuilder(String localName, double rotationDeg){
        this(localName, rotationDeg, null);
    }

    public GradientFragmentBuilder(String localName, IShaderPipeline pipeline){
        this(localName,0, pipeline);
    }

    public GradientFragmentBuilder(String localName, double rotationDeg, IShaderPipeline pipeline){
        this.rotationDeg = rotationDeg;
        this.localName = localName;
        this.pipeline = pipeline;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder setRotation(double degrees){
        this.rotationDeg = degrees;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder setInterpolationType(InterpolationType type){
        if(type == InterpolationType.NONE){
            log.warn("Improper API Usage: A gradient requires interpolation - that's like the whole deal. The attempted value of " + type + " has been ignored.");
            return this;
        }

        this.interpolationType = type;
        return this;
    }

    private static final String glslConstantsDec = """
    #ifdef GL_ES
        precision mediump float;
    #endif
    
    varying vec2 v_texCoords;
    """ +
    "uniform float "+ GradientShaderAttr.ROTATION +";\n" +
    "vec2 direction = vec2(cos("+ GradientShaderAttr.ROTATION +"), sin("+ GradientShaderAttr.ROTATION +"));\n" +
    """
    
    const vec2 center = vec2(0.5, 0.5);
    const vec2 preRotationDirection = vec2(1.0, 0.0);
    """;

    private static final String glslStartMain = """
    void main() {
        vec2 adjustedCoords = v_texCoords - center;
        vec2 rotatedCoords = adjustedCoords * mat2(direction.x, -direction.y, direction.y, direction.x);
        rotatedCoords += center;
        float progress = dot(rotatedCoords - center, preRotationDirection) + 0.5;
        vec4 color = vec4(color0);
    """;

    /** {@inheritDoc} */
    @Override
    public IGradientShader build() {
        StringBuilder codeBuilder = new StringBuilder();

        // Initialize shader and define varying v_texCoords
        codeBuilder.append(glslConstantsDec);

        // Constants for color stops
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            String colorAsString = color.r +", " + color.g+", "+color.b+", "+color.a;
            codeBuilder.append("const vec4 color"+i+" = vec4("+colorAsString+");\n")
                  .append("const float position"+i+" = "+positions.get(i)+";\n");
        }

        // Main shader logic
        codeBuilder.append(glslStartMain);
        appendStopStatements(codeBuilder);

        codeBuilder.append(
            "\tgl_FragColor = color;\n" +
        "}");

        FragmentShader fragment = new FragmentShader(localName, codeBuilder.toString());
        IGradientShader wrapped = new GradientShader(localName, VertexShader.DEFAULT, fragment, true, new ArrayList<>());
        if(pipeline != null){
            pipeline.registerForCompilation(wrapped);
        }
        wrapped.setRotation(rotationDeg);

        return wrapped;
    }

    private void appendStopStatements(StringBuilder shader) {
        shader.append("\tif (progress >= position"+0+" && progress <= position"+1+") {\n")
                .append("\t\tfloat t = " + appendInterpolationFunction(0) + "\n")
                .append("\t\tcolor = mix(color"+0+", color"+1+", t);\n")
                .append("\t}");

        if(colors.size() <= 1){
            shader.append("\n");
            return;
        }else{
            shader.append(" else ");
        }
        boolean hadSubSteps = false;
        for (int i = 1; i < colors.size() - 1; i++) {
            hadSubSteps = true;
            shader.append("if (progress >= position"+i+" && progress <= position"+(i+1)+") {\n")
                  .append("\t\tfloat t = " + appendInterpolationFunction(i) + "\n")
                  .append("\t\tcolor = mix(color"+i+", color"+(i+1)+", t);\n")
                  .append("\t}");
        }

        if(hadSubSteps){
            shader.append(" else ");
        }

        shader.append("if (progress > position" + (colors.size() - 1) + ") {\n")
                .append("\t\tcolor = color" + (colors.size() - 1) + ";\n")
                .append("\t}\n");
    }

    private String appendInterpolationFunction(int stepIndex){
        return switch (interpolationType) {
            case HERMIT -> "smoothstep(position"+stepIndex+", position"+(stepIndex+1)+", progress);";
            case LINEAR, NONE -> "(progress - position"+stepIndex+") / (position"+(stepIndex+1)+" - position"+stepIndex+");";
            case QUADRATIC -> "(progress - position"+stepIndex+") / (position"+(stepIndex+1)+" - position"+stepIndex+");\n" +
                    "\t\tif (t < 0.5) {\n" +
                    "\t\t\tt = 2.0 * t * t; \n" +
                    "\t\t} else {\n" +
                    "\t\t\tt = -1.0 + (4.0 - 2.0 * t) * t;\n" +
                    "\t\t}";
            case LOGARITHMIC -> "position"+stepIndex+" + position" + (stepIndex+1) + " * (1.0 - ( 1.0 / (progress + 1.0)));";
        };
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder addStops(Color color, double relativePosition){
        colors.add(color);
        positions.add(relativePosition);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1) {
        colors.addAll(List.of(c0, c1));
        positions.addAll(List.of(pos0, pos1));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1, Color c2, double pos2) {
        colors.addAll(List.of(c0, c1, c2));
        positions.addAll(List.of(pos0, pos1, pos2));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1, Color c2, double pos2, Color c3, double pos3) {
        colors.addAll(List.of(c0, c1, c2, c3));
        positions.addAll(List.of(pos0, pos1, pos2, pos3));
        return this;
    }
}
