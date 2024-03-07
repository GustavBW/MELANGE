package gbw.melange.common.elementary;

import com.badlogic.gdx.math.Quaternion;

public interface IComputedTransforms {
    void update();
    /**
     * Absolute
     */
    double getHeight();
    /**
     * Absolute
     */
    double getWidth();
    /**
     * Absolute
     */
    double getPositionX();

    /**
     * Absolute
     */
    double getPositionY();
    //TODO: Find a way to simplify this. I don't understand it either, but know its how rotation works in 3D
    Quaternion getRotation();
}
