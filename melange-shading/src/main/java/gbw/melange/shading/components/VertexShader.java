package gbw.melange.shading.components;


import gbw.melange.common.shading.components.IVertexShader;
import gbw.melange.common.shading.constants.GLShaderType;
import gbw.melange.common.shading.constants.ShaderClassification;

/**
 * String alias for now
 */
public final class VertexShader extends ShaderComponent implements IVertexShader {

    public VertexShader(String localName, String code) {
        super(GLShaderType.VERTEX, localName, code);
    }

    @Override
    public void unbindAny(){

    }

    @Override
    public ShaderClassification getClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void applyBindings() {

    }

    public static final VertexShader DEFAULT = new VertexShader("MELANGE_DEFAULT_VERTEX", """
                #ifdef GL_ES
                    precision mediump float;
                #endif
                
                attribute vec4 a_position;
                attribute vec2 a_texCoord0;
                uniform mat4 u_projTrans;
                varying vec2 v_texCoords;
                
                void main() {
                    v_texCoords = a_texCoord0;
                    gl_Position =  u_projTrans * a_position;
                }
            """);
}
