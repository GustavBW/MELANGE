package gbw.melange.shading.templating.gradients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.FragmentShader;

import java.util.ArrayList;
import java.util.List;

public class GradientFragmentShaderBuilder {

    private double rotationDeg = 0;
    private List<Color> colors = new ArrayList<>();
    private List<Double> positions = new ArrayList<>();

    public static GradientFragmentShaderBuilder create(double rotation){
        return new GradientFragmentShaderBuilder(rotation);
    }

    public GradientFragmentShaderBuilder(double rotationDeg){
        this.rotationDeg = rotationDeg;
    }

    public GradientFragmentShaderBuilder addStop(Color color, double relativePosition){
        colors.add(color);
        positions.add(relativePosition);
        return this;
    }
    public void setConstantProperties(ShaderProgram compiledShader){

    }
    public FragmentShader build() {
        StringBuilder shader = new StringBuilder();

        // GLSL version and precision
        shader.append("#ifdef GL_ES\n")
                .append("precision mediump float;\n")
                .append("#endif\n")
                .append("varying vec2 v_texCoords;\n"); // Assuming v_texCoords are passed from the vertex shader

        // Constants for rotation
        double radians = Math.toRadians(rotationDeg);
        shader.append("const float angle = "+radians+";\n")
              .append("const vec2 direction = vec2(cos(angle), sin(angle));\n");

        // Constants for color stops
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            String colorAsString = color.r +", " + color.g+", "+color.b+", "+color.a;
            shader.append("const vec4 color"+i+" = vec4("+colorAsString+");\n")
                  .append("const float position"+i+" = "+positions.get(i)+";\n");
        }

        // Main shader logic
        shader.append("void main() {\n")
                .append("  float progress = dot(v_texCoords, direction);\n");

        // Color interpolation logic
        shader.append("  vec4 color = vec4(0.0);\n");
        for (int i = 0; i < colors.size() - 1; i++) {
            shader.append("  if (progress >= position"+i+" && progress <= position"+(i+1)+") {\n")
                  .append("    float t = (progress - position"+i+") / (position"+(i+1)+" - position"+i+");\n")
                  .append("    color = mix(color"+i+", color"+(i+1)+", t);\n")
                  .append("  }\n");
        }
        appendGlSetColorStatement(shader);

        return new FragmentShader(shader.toString());
    }

    private static void appendGlSetColorStatement(StringBuilder shader) {
        shader.append("  gl_FragColor = color;\n")
                .append("}\n");
    }
}
