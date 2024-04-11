package gbw.melange.model.node;

import gbw.melange.model.typealias.GLObjectHandle;

public interface IGLObject<T extends IBaseNode<?>> {
    GLObjectHandle getHandle();
    Class<T> nodeRepressentation();
}
