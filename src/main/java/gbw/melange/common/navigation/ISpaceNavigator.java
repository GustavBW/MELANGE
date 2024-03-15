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
     * <p>goToSpace.</p>
     *
     * @param space a {@link gbw.melange.common.elementary.space.ISpace} object
     */
    void goToSpace(ISpace space);
    /**
     * Get all spaces in the reverse order they appear on screen.
     * I.e. the foremost space will be the last entry.
     *
     * @return a {@link java.util.List} object
     */
    List<ISpace> getOrderedList();

    List<ISpace> getVisibleSpaces();
}
