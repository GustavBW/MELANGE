package gbw.melange.elements.navigation;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.ISpaceRegistry;
import gbw.melange.common.elementary.space.SpaceLayerEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpaceManager implements ISpaceManager{
    private final List<ISpace> ordered = new ArrayList<>();
    @Override
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

    @Override
    public void goToLayer(int layer) {

    }

    @Override
    public List<ISpace> getOrderedList() {
        return ordered;
    }
}
