package gbw.melange.common.mesh.formatting.slicing;

/**
 * An {@link IFloatSlice} of size 2, but formatted as if a 2-component vector.
 * Extended by {@link ISliceVec3} and {@link ISliceVec4} which allows for "polymorphic access control"
 * if say a {@link ISliceVec3} is referenced as a {@link ISliceVec2} - this way removing access to the third component.
 *
 * @author GustavBW
 */
public interface ISliceVec2 extends IFloatSlice {
    /**
     * Get
     */
    float x();
    /**
     * Get
     */
    float y();
    /**
     * Set
     */
    void x(float value);
    /**
     * Set
     */
    void y(float value);
}
