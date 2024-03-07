package gbw.melange.common.elementary.space;

import java.util.List;
import java.util.Map;

/**
 * For all intends and purposes this is a singleton. However, managed by Spring.
 */
public interface ISpaceRegistry {
    void register(ISpace space, Object object);
    void register(ISpace space, Class<?> clazz);
    Map<Class<?>, List<SpaceLayerEntry>> get();
}
