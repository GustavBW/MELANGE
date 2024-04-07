package gbw.melange.core.elementary;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.SpaceLayerEntry;
import gbw.melange.common.navigation.ISpaceNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>SpaceNavigator class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class SpaceNavigator implements ISpaceNavigator {
    private static final Logger log = LogManager.getLogger();
    private final List<ISpace> ordered = new ArrayList<>();
    /**
     * Lookup table for all configurations and policies assigned to this space.
     */
    private final Map<ISpace, SpaceLayerEntry> spaceMetadata = new HashMap<>();
    private final List<ISpace> onScreenCurrently = new LinkedList<>();

    /**
     * <p>loadFromRegistry.</p>
     *
     * @param registry a {@link gbw.melange.core.elementary.ISpaceRegistry} object
     */
    public void loadFromRegistry(ISpaceRegistry registry) {
        // Clear the existing ordered list to reload it from the registry
        ordered.clear();
        onScreenCurrently.clear();

        // Get the map from the registry
        Map<Class<?>, List<SpaceLayerEntry>> registryMap = registry.get();

        // Flatten the map values (List<SpaceLayerEntry>) to a single list and then sort it
        List<SpaceLayerEntry> sortedEntries = registryMap.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(SpaceLayerEntry::layer))
                .toList()
                //TODO: Test if lowest layer value is placed last so that onScreenCurrently contains the right space
                .reversed();

        // Extract ISpace instances from the sorted SpaceLayerEntry list
        for (SpaceLayerEntry entry : sortedEntries) {
            ordered.add(entry.spaceInstance());
            spaceMetadata.put(entry.spaceInstance(), entry);
        }
        //Ordered is by layer, so adding
        onScreenCurrently.addLast(ordered.getLast());
    }

    /** {@inheritDoc} */
    @Override
    public void goToSpace(ISpace space) {
        //Shove to the back to have be rendered last, and thus on top.
        SpaceLayerEntry metadata = spaceMetadata.get(space);
        switch (metadata.focusPolicy()){
            case FocusPolicy.OPAQUE -> onScreenCurrently.clear();
            case FocusPolicy.RETAIN_LATEST -> {
                if (!onScreenCurrently.isEmpty()){
                    //Grab the topmost, clear all, and put that back in
                    ISpace formerTopMost = onScreenCurrently.getLast();
                    onScreenCurrently.clear();
                    onScreenCurrently.add(formerTopMost);
                }
            }
            case FocusPolicy.RETAIN_ALL -> {} //Intentional NOOP
            default -> {
                log.warn("Unknown focus policy: " + metadata.focusPolicy() + " update ISpaceNavigator handling method");
            }
        }
        onScreenCurrently.addLast(space);
    }

    /** {@inheritDoc} */
    @Override
    public List<ISpace> getOrderedList() {
        return ordered;
    }

    @Override
    public List<ISpace> getVisibleSpaces() {
        return onScreenCurrently;
    }
}
