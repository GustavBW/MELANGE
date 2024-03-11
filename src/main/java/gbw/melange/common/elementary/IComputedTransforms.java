package gbw.melange.common.elementary;

import com.badlogic.gdx.math.Quaternion;

public interface IComputedTransforms {
    /**
     * Updates the internal state of the transforms. This should be called after any changes to the matrix.
     */
    void update();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     */
    double getHeight();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     */
    double getWidth();
    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     */
    double getPositionX();
    /**
     * Returns the axis aligned bounds of the element in the form of a double array.
     * The array is in the form of: [x, y, width, height] disregards rotation and is not necessarily the exact extends of the mesh.
     */
    double[] getAxisAlignedBounds();

    /**
     * Relative to screen space dimensions, in the range of -1 to 1
     */
    double getPositionY();
    //TODO: Find a way to simplify this. I don't understand it either, but know its how rotation works in 3D
    Quaternion getRotation();
}
