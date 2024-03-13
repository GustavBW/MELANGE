package gbw.melange.elements.events;

import com.badlogic.gdx.math.Vector3;

/**
 * <p>IElementMoveEvent interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementMoveEvent extends IElementEvent {
    /**
     * Normalized 3D vector
     *
     * @return a {@link com.badlogic.gdx.math.Vector3} object
     */
    Vector3 direction();
    /**
     * <p>distance.</p>
     *
     * @return a double
     */
    double distance();

}
