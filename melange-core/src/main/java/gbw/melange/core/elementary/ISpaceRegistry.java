package gbw.melange.core.elementary;

import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.elementary.space.SpaceLayerEntry;

import java.util.List;
import java.util.Map;

/**
 * For all intends and purposes this is a singleton. However, managed by Spring.
 * Its a temprorary object used to gather information about all spaces. It is not used during main runtime, see ISpaceNavigator instead.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ISpaceRegistry {
    /**
     * <p>register.</p>
     *
     * @param space a {@link gbw.melange.common.elementary.space.ISpace} object
     * @param object a {@link java.lang.Object} object
     */
    void register(ISpace space, Object object);
    /**
     * <p>register.</p>
     *
     * @param space a {@link gbw.melange.common.elementary.space.ISpace} object
     * @param clazz a {@link java.lang.Class} object
     */
    void register(ISpace space, Class<?> clazz);
    /**
     * <p>get.</p>
     *
     * @return a {@link java.util.Map} object
     */
    Map<Class<?>, List<SpaceLayerEntry>> get();
}
