package gbw.melange.model;

import gbw.melange.model.node.IGLObject;
import gbw.melange.model.node.ICenterNode;
import gbw.melange.model.node.NodeID;
import gbw.melange.common.errors.Error;

public interface IModel {
    /**
     * Add a node to this model.
     * @param object anything implementing the IGLObject interface.
     * @return An {@link ICenterNode} of that {@link IGLObject} including the generated {@link NodeID}
     */
    <T extends IGLObject> ICenterNode<T> addNode(IGLObject object);

    /**
     * Check the current configuration.
     * @return an error if any, or {@link Error#NONE}
     */
    Error validate();
}
