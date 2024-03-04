package gbw.melange.shading;

import java.util.Objects;

/**
 * String alias for now
 */
public class FragmentShader {

    private final String code;

    public FragmentShader(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }


    public static final String DEFAULT = """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
        varying vec4 v_color;
        varying vec2 v_texCoords;
        
        void main() {
            // Define the start (bottom) and end (top) colors of the gradient
            vec4 startColor = vec4(1.0, 0.0, 1.0, 1.0); // Blue
            vec4 endColor = vec4(1.0, 0.0, 1.0, 0.0); // Red
        
            // Interpolate between the start and end colors based on the vertical texture coordinate
            vec4 gradientColor = mix(startColor, endColor, v_texCoords.y);
        
            gl_FragColor = gradientColor;
        }
    """;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FragmentShader) obj;
        return Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "FragmentShader[" +
                "code=" + code + ']';
    }


}
