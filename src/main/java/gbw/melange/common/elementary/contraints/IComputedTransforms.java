package gbw.melange.common.elementary.contraints;

import com.badlogic.gdx.math.Quaternion;

/**
 * <p>IComputedTransforms interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IComputedTransforms {
    /**
     * Updates the internal state of the transforms. This should be called after any changes to the matrix.
     */
    void update();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     *
     * @return a double
     */
    double getHeight();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     *
     * @return a double
     */
    double getWidth();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     *
     * @return a double
     */
    double getPositionX();
    /**
     * Returns the axis aligned bounds of the element in the form of a double array.
     * The array is in the form of: [x, y, width, height] disregards rotation and is not necessarily the exact extends of the mesh.
     */
    double[] getAxisAlignedBounds();

    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     *
     * @return a double
     */
    double getPositionY();
    //TODO: Find a way to simplify this. I don't understand it either, but know its how rotation works in 3D
    /**
     * <p>getRotation.</p>
     *
     * @return a {@link com.badlogic.gdx.math.Quaternion} object
     */
    Quaternion getRotation();
}
