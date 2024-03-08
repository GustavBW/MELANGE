package gbw.melange.shading;

/**
 * String alias for now
 */
public record VertexShader(String code) {
    public static final VertexShader DEFAULT = new VertexShader("""
        attribute vec4 a_position;
        attribute vec2 a_texCoord0;
        uniform mat4 u_projTrans;
        varying vec2 v_texCoords;
        void main() {
            v_texCoords = a_texCoord0;
            gl_Position =  u_projTrans * a_position;
        }
    """);
    public static final VertexShader NONE = new VertexShader(
    """
        void main() {
            gl_Position = 0;
        }
        """
    );
}
