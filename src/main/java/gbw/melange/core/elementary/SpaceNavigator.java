package gbw.melange.core.elementary;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.core.elementary.ISpaceRegistry;
import gbw.melange.common.elementary.space.SpaceLayerEntry;
import gbw.melange.common.navigation.ISpaceNavigator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * <p>SpaceNavigator class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class SpaceNavigator implements ISpaceNavigator {
    private final List<ISpace> ordered = new ArrayList<>();
    /**
     * <p>loadFromRegistry.</p>
     *
     * @param registry a {@link gbw.melange.core.elementary.ISpaceRegistry} object
     */
    public void loadFromRegistry(ISpaceRegistry registry) {
        // Clear the existing ordered list to reload it from the registry
        ordered.clear();

        // Get the map from the registry
        Map<Class<?>, List<SpaceLayerEntry>> registryMap = registry.get();

        // Flatten the map values (List<SpaceLayerEntry>) to a single list and then sort it
        List<SpaceLayerEntry> sortedEntries = registryMap.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(SpaceLayerEntry::layer))
                .toList();

        // Extract ISpace instances from the sorted SpaceLayerEntry list
        for (SpaceLayerEntry entry : sortedEntries) {
            ordered.add(entry.spaceInstance());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void goToLayer(int layer) {

    }

    /** {@inheritDoc} */
    @Override
    public void goToSpace(ISpace space) {

    }

    /** {@inheritDoc} */
    @Override
    public List<ISpace> getOrderedList() {
        return ordered;
    }
}
