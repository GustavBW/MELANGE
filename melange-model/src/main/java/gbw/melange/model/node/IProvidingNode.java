package gbw.melange.model.node;

import java.util.List;

/**
 * Provides only node. Has no requirements. Just is. Deal with it.
 */
public interface IProvidingNode<T extends IGLObject> extends IBaseNode {
    List<INodeID<?>> provides();
}
