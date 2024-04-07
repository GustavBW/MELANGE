package gbw.melange.shading.components;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.constants.GLShaderType;
import gbw.melange.shading.constants.ShaderClassification;
import org.lwjgl.opengl.GL30;

/**
 * @author GustavBW
 */
public class FragmentShader extends ShaderComponent implements IShader {

    private final ShaderClassification classification;
    private boolean isStatic;


    public FragmentShader(String localName, String code) {
        this(localName, code, ShaderClassification.COMPLEX);
    }
    public FragmentShader(String localName, String code, ShaderClassification classification){
        this(localName, code, classification, true);
    }
    public FragmentShader(String localName, String code, ShaderClassification classification, boolean isStatic){
        super(GLShaderType.FRAGMENT, localName, code);
        this.classification = classification;
        this.isStatic = isStatic;
    }

    @Override
    public void applyBindings() {

    }

    @Override
    public void unbindAny(){

    }

    public ShaderClassification getClassification(){
        return classification;
    }
    public boolean isStatic(){
        return isStatic;
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

    public static final FragmentShader DEFAULT = new FragmentShader("MELANGE_DEFAULT_FRAGMENT",
    """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
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
    public static final FragmentShader DEBUG_UV = new FragmentShader("MELANGE_DEBUG_UV_FRAGMENT",
        """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
        varying vec2 v_texCoords;
            
        void main() {
            gl_FragColor = vec4(v_texCoords.x, v_texCoords.y, 0, 1);
        }
    """, ShaderClassification.COMPLEX, false); //Set to complex and not static to make sure it updates every frame as this is for debugging

    public static final FragmentShader TRANSPARENT = FragmentShader.constant(new Color(0,0,0,0));

    public static final FragmentShader TEXTURE = new FragmentShader("MELANGE_TEXTURE_FRAGMENT",
    """
        #ifdef GL_ES
        precision mediump float;
        #endif
        
        varying vec2 v_texCoords;
        """ +
        "uniform sampler2D "+ GLShaderAttr.TEXTURE.glValue() +";\n" +
        """
        void main() {
        """ +
            "\tgl_FragColor = texture2D("+ GLShaderAttr.TEXTURE.glValue() +", v_texCoords);\n" +
        """
        }
    """, ShaderClassification.PURE_SAMPLER);


}
