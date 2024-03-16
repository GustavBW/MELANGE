package gbw.melange.shading.impl;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.shading.ShaderClassification;

import java.util.Objects;

/**
 * String alias for now
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class FragmentShader {

    private final String code;
    private final String localName;
    private ShaderClassification classification;

    /**
     * <p>Constructor for FragmentShader.</p>
     *
     * @param localName a {@link java.lang.String} object
     * @param code a {@link java.lang.String} object
     */
    public FragmentShader(String localName, String code) {
        this(localName, code, ShaderClassification.COMPLEX);
    }
    public FragmentShader(String localName, String code, ShaderClassification classification){
        this.code = code;
        this.localName = localName;
        this.classification = classification;
    }

    /**
     * <p>GLSL code.</p>
     */
    public String code() {
        return code;
    }
    /**
     * <p>Debugging name.</p>
     */
    public String name(){
        return localName;
    }
    public ShaderClassification getClassification(){
        return classification;
    }
    public void setClassification(ShaderClassification classification){
        this.classification = classification;
    }

    /**
     * Generate a glsl fragment shader showing a given constant color
     */
    public static FragmentShader constant(Color color){
        // Convert the RGBA color components to GLSL float literals
        float r = color.r, g = color.g, b = color.b, a = color.a;

        final String code = "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +
                "void main() {\n" +
                "\tgl_FragColor = vec4(" + r + ", " + g + ", " + b + ", " + a + ");\n" +
                "}\n";

        return new FragmentShader("CONSTANT_RGBA_FRAGMENT("+r+","+g+","+b+","+a+")",code, ShaderClassification.SIMPLE);
    }

    /** Constant <code>DEFAULT</code> */
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
    """, ShaderClassification.COMPLEX);
    /** Constant <code>DEBUG_UV</code> */
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
    """, ShaderClassification.COMPLEX); //Set to complex to make sure it updates as this is debugging

    /** Constant <code>TRANSPARENT</code> */
    public static final FragmentShader TRANSPARENT = FragmentShader.constant(new Color(0,0,0,0));

    /** Constant <code>TEXTURE</code> */
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
    """, ShaderClassification.PURE_SAMPLER);

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FragmentShader) obj;
        return Objects.equals(this.code, that.code);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "FragmentShader[" +
                "code=" + code + ']';
    }


}
