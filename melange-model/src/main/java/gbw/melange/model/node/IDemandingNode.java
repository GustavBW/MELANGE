package gbw.melange.model.node;

import java.util.List;

public interface IDemandingNode<T extends IGLObject<?>> extends IBaseNode<T> {
    List<INodeID<?>> requires();
}
