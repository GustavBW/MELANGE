package gbw.melange.elements.events;

import com.badlogic.gdx.math.Vector3;

public interface IElementMoveEvent extends IElementEvent {
    /**
     * Normalized 3D vector
     */
    Vector3 direction();
    double distance();

}
