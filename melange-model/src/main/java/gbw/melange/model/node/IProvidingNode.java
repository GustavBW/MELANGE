package gbw.melange.model.node;

/**
 * Provides only node. Has no requirements. Just is. Deal with it.
 */
public interface IProvidingNode<T extends IGLObject> {

    NodeID<?> provides();

}
