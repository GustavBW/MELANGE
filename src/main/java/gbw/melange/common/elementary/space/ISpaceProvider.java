package gbw.melange.common.elementary.space;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * For all intends and purposes this is a singleton. However, managed by Spring.
 */
public interface ISpaceProvider<T extends ISpace> {
    /**
     * Provides a new managed, IScreenSpace instance.
     * @param forWhom NOT NULL. A ISpace instance can only be managed, if properties of the class that retrieves the instance can be read.
     */
    T getScreenSpace(Object forWhom);
}
