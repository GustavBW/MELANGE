package gbw.melange.common.hooks;

/**
 * Append anything during the render cycle
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@FunctionalInterface
public interface OnRender {
    /**
     * <p>onRender.</p>
     *
     * @param deltaT a double
     */
    void onRender(double deltaT);
}
