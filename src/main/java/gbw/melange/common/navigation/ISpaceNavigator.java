package gbw.melange.common.navigation;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.core.elementary.ISpaceRegistry;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ISpaceNavigator {
    void goToLayer(int layer);
    void goToSpace(ISpace space);
    List<ISpace> getOrderedList();
}
