package gbw.melange.shading;

import com.badlogic.gdx.graphics.Color;

import java.util.Objects;

/**
 * String alias for now
 */
public class FragmentShader {
    private final String code;
    private final String localName;

    public FragmentShader(String localName, String code) {
        this.code = code;
        this.localName = localName;
    }

    public String code() {
        return code;
    }
    public String name(){
        return localName;
    }

    public static FragmentShader constant(Color color){
        // Convert the RGBA color components to GLSL float literals
        float r = color.r, g = color.g, b = color.b, a = color.a;

        final String code = "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +
                "void main() {\n" +
                "\tgl_FragColor = vec4(" + r + ", " + g + ", " + b + ", " + a + ");\n" +
                "}\n";

        return new FragmentShader("CONSTANT_RGBA_FRAGMENT("+r+","+g+","+b+","+a+")",code);
    }

    public static final FragmentShader DEFAULT = new FragmentShader("MELANGE_DEFAULT_FRAGMENT",
    """
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
    """);
    public static final FragmentShader DEBUG_UV = new FragmentShader("MELANGE_DEBUG_UV_FRAGMENT",
        """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
        varying vec4 v_color;
        varying vec2 v_texCoords;
            
        void main() {
            gl_FragColor = vec4(v_texCoords.x, v_texCoords.y, 0, 1);
        }
    """);

    public static final FragmentShader TRANSPARENT = new FragmentShader("MELANGE_TRANSPARENT_FRAGMENT",
    """
        #ifdef GL_ES
        precision mediump float;
        #endif
                
        const vec4 color = vec4(0,0,0,0);
            
        void main() {
            gl_FragColor = color;
        }
        """
    );
    public static final FragmentShader TEXTURE = new FragmentShader("MELANGE_TEXTURE_FRAGMENT",
    """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
        varying vec2 v_texCoords;
        uniform sampler2D u_texture;
        
        void main() {
            gl_FragColor = texture2D(u_texture, v_texCoords);
        }
    """);

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
