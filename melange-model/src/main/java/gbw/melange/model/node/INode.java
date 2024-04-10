package gbw.melange.model.node;

import java.util.List;

public interface INode<T extends IGLObject> {

    List<NodeID<?>> requires();
    List<NodeID<?>> provides();

    T get();

    NodeID<T> getId();

}
