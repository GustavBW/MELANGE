package gbw.melange.model.node;

import java.util.function.Consumer;

public interface IBaseNode<T extends IGLObject> {
    void modify(Consumer<T> func);
    NodeID<T> getId();
}
