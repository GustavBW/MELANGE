package gbw.melange.common.navigation;

import gbw.melange.common.elementary.space.ISpace;

import java.util.List;


/**
 * <p>ISpaceNavigator interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ISpaceNavigator {
    /**
     * <p>goToLayer.</p>
     *
     * @param layer a int
     */
    void goToLayer(int layer);
    /**
     * <p>goToSpace.</p>
     *
     * @param space a {@link gbw.melange.common.elementary.space.ISpace} object
     */
    void goToSpace(ISpace space);
    /**
     * <p>getOrderedList.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<ISpace> getOrderedList();
}
