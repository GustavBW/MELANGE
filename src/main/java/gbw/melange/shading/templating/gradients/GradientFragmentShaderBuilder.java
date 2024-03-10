package gbw.melange.shading.templating.gradients;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.builders.IBuilder;
import gbw.melange.shading.FragmentShader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradientFragmentShaderBuilder implements IGradientBuilder {

    private double rotationDeg = 0;
    private InterpolationType interpolationType = InterpolationType.HERMIT;
    private List<Color> colors = new ArrayList<>();
    private List<Double> positions = new ArrayList<>();
    private String localName;

    public GradientFragmentShaderBuilder(String localName){
        this(localName,0);
    }

    public GradientFragmentShaderBuilder(String localName, double rotationDeg){
        this.rotationDeg = rotationDeg;
        this.localName = localName;
    }

    @Override
    public IGradientBuilder setRotation(double degrees){
        this.rotationDeg = degrees;
        return this;
    }

    @Override
    public IGradientBuilder setInterpolationType(InterpolationType type){
        this.interpolationType = type;
        return this;
    }
    @Override
    public FragmentShader build() {
        StringBuilder codeBuilder = new StringBuilder();

        // Initialize shader and define varying v_texCoords
        codeBuilder.append("#ifdef GL_ES\n")
                .append("precision mediump float;\n")
                .append("#endif\n")
                .append("varying vec2 v_texCoords;\n");

        // Calculate rotation in radians
        double radians = Math.toRadians(rotationDeg);
        // Calculate directional vector for the gradient post-rotation
        codeBuilder.append("const float angle = ").append(radians).append(";\n")
                .append("const vec2 center = vec2(0.5, 0.5);\n") // Center for rotation
                .append("const vec2 preRotationDirection = vec2(1.0, 0.0);\n") // Horizontal gradient pre-rotation
                .append("const vec2 direction = vec2(cos(angle), sin(angle));\n"); // Direction after rotation

        // Constants for color stops
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            String colorAsString = color.r +", " + color.g+", "+color.b+", "+color.a;
            codeBuilder.append("const vec4 color"+i+" = vec4("+colorAsString+");\n")
                  .append("const float position"+i+" = "+positions.get(i)+";\n");
        }

        // Main shader logic
        codeBuilder.append("void main() {\n")
                // Adjust v_texCoords to rotate around the center
                .append("\tvec2 adjustedCoords = v_texCoords - center;\n") // Move pivot to center
                .append("\tvec2 rotatedCoords = adjustedCoords * mat2(direction.x, -direction.y, direction.y, direction.x);\n") // Apply rotation
                .append("\trotatedCoords += center;\n") // Move pivot back
                // Calculate progress in the rotated coordinate space
                .append("\tfloat progress = dot(rotatedCoords - center, preRotationDirection) + 0.5;\n");

        // Color interpolation logic
        codeBuilder.append("\tvec4 color = vec4(color0);\n");
        appendStops(codeBuilder);

        appendGlSetColorStatement(codeBuilder);

        return new FragmentShader(localName, codeBuilder.toString());
    }

    private void appendStops(StringBuilder shader) {
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
                .append("\t}");
    }

    private String appendInterpolationFunction(int stepIndex){
        return switch (interpolationType) {
            case HERMIT -> "smoothstep(position"+stepIndex+", position"+(stepIndex+1)+", progress);";
            case LINEAR -> "(progress - position"+stepIndex+") / (position"+(stepIndex+1)+" - position"+stepIndex+");";
            case QUADRATIC -> "(progress - position"+stepIndex+") / (position"+(stepIndex+1)+" - position"+stepIndex+");\n" +
                    "\t\tif (t < 0.5) {\n" +
                    "\t\t\tt = 2.0 * t * t; \n" +
                    "\t\t} else {\n" +
                    "\t\t\tt = -1.0 + (4.0 - 2.0 * t) * t;\n" +
                    "\t\t}";
        };
    }

    private void appendGlSetColorStatement(StringBuilder shader) {
        shader.append("\tgl_FragColor = color;\n")
                .append("}\n");
    }
    @Override
    public IGradientBuilder addStops(Color color, double relativePosition){
        colors.add(color);
        positions.add(relativePosition);
        return this;
    }

    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1) {
        colors.addAll(List.of(c0, c1));
        positions.addAll(List.of(pos0, pos1));
        return this;
    }

    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1, Color c2, double pos2) {
        colors.addAll(List.of(c0, c1, c2));
        positions.addAll(List.of(pos0, pos1, pos2));
        return this;
    }

    @Override
    public IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1, Color c2, double pos2, Color c3, double pos3) {
        colors.addAll(List.of(c0, c1, c2, c3));
        positions.addAll(List.of(pos0, pos1, pos2, pos3));
        return this;
    }
}
