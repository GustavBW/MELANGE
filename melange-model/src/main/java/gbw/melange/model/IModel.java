package gbw.melange.model;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import gbw.melange.model.node.IBaseNode;
import gbw.melange.model.node.IGLObject;
import gbw.melange.model.node.ICenterNode;
import gbw.melange.model.node.INodeID;
import gbw.melange.common.errors.Error;

public interface IModel {
    /**
     * Add a node to this model.
     * @param object anything implementing the IGLObject interface.
     * @return An {@link ICenterNode} of that {@link IGLObject} including the generated {@link INodeID}
     */
    <T extends IGLObject<?>, R extends IBaseNode<T>> R addNode(IGLObject<R> object);

    /**
     * Check the current configuration.
     * @return an error if any, or {@link Error#NONE}
     */
    Error validate();
    void render(FrameBuffer fbo);
}
