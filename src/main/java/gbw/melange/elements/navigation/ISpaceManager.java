package gbw.melange.elements.navigation;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.ISpaceRegistry;

import java.util.List;

public interface ISpaceManager {
    void loadFromRegistry(ISpaceRegistry registry);
    void goToLayer(int layer);
    List<ISpace> getOrderedList();
}
