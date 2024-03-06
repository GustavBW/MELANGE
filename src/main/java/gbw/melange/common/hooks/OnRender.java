package gbw.melange.common.hooks;

/**
 * Append anything during the render cycle
 */
@FunctionalInterface
public interface OnRender {
    void onRender();
}
