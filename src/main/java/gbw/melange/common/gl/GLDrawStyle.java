package gbw.melange.common.gl;


import com.badlogic.gdx.graphics.GL30;

/**
 * <p>GLDrawStyle class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum GLDrawStyle {
    INVALID(-1),
    TRIANGLES(GL30.GL_TRIANGLES),
    TRIANGLE_STRIP(GL30.GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL30.GL_TRIANGLE_FAN),
    LINES(GL30.GL_LINES),
    LINE_STRIP(GL30.GL_LINE_STRIP),
    LINE_LOOP(GL30.GL_LINE_LOOP),
    POINTS(GL30.GL_POINTS);

    public final int value;

    GLDrawStyle(int gl30value) {
        this.value = gl30value;
    }

    /**
     * Get the equivalent GLDrawStyle from the GL30 integer constant
     */
    public static GLDrawStyle fromGL30(int gl30Value) {
        for (GLDrawStyle style : values()) {
            if (style.value == gl30Value) {
                return style;
            }
        }
        return INVALID;
    }
}
