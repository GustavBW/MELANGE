package gbw.melange.common.gl_wrappers;

import org.lwjgl.opengl.GL20;

/**
 * <p>GLDrawStyle class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum GLDrawStyle {
    INVALID(-1),
    TRIANGLES(GL20.GL_TRIANGLES),
    TRIANGLE_STRIP(GL20.GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL20.GL_TRIANGLE_FAN),
    LINES(GL20.GL_LINES),
    LINE_STRIP(GL20.GL_LINE_STRIP),
    LINE_LOOP(GL20.GL_LINE_LOOP),
    POINTS(GL20.GL_POINTS);

    public final int value;

    GLDrawStyle(int gl20Value) {
        this.value = gl20Value;
    }

    /**
     * <p>fromGL20.</p>
     *
     * @param gl20Value a int
     * @return a {@link gbw.melange.common.gl_wrappers.GLDrawStyle} object
     */
    public static GLDrawStyle fromGL20(int gl20Value) {
        for (GLDrawStyle style : values()) {
            if (style.value == gl20Value) {
                return style;
            }
        }
        return INVALID;
    }
}
