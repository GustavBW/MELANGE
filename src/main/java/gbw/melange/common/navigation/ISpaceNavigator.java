package gbw.melange.common.navigation;

import gbw.melange.common.elementary.space.ISpace;

import java.util.List;


public interface ISpaceNavigator {
    void goToLayer(int layer);
    void goToSpace(ISpace space);
    List<ISpace> getOrderedList();
}
