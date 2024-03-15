package gbw.melange.shading;

/**
 * String alias for now
 */
public record VertexShader(String localName, String code) {
    public static final VertexShader DEFAULT = new VertexShader("MELANGE_DEFAULT_VERTEX","""
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
    public static final VertexShader NONE = new VertexShader("MELANGE_NONE_VERTEX",
    """
        #ifdef GL_ES
            precision mediump float;
        #endif
        const vec4 nothing = vec4(0,0,0,0);
        void main() {
            gl_Position = nothing;
        }
        """
    );

}
