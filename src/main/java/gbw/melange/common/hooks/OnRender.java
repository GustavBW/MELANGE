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
     * Will get invoked every frame, as the very last things that are evaluated.
     * Already managed elements, meshes and shaders can be modified by classes using this hook, but very much shouldn't be as they are already taken care of.
     *
     * @param deltaT a double
     */
    void onRender(double deltaT);
}
