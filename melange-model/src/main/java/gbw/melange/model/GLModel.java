package gbw.melange.model;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import gbw.melange.common.errors.Error;
import gbw.melange.model.errors.MalformedGLObjectIssue;
import gbw.melange.model.node.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GLModel implements IModel {
    private final Map<INodeID<?>, IBaseNode<?>> nodesByID = new HashMap<>();
    private final Map<INodeID<?>, IProvidingNode<?>> providedResourcesByID = new HashMap<>();
    private final Map<INodeID<?>, IDemandingNode<?>> requiredResourcesByID = new HashMap<>();
    //private final Map<INodeID<?>, IRenderableNode<?>> nodesThatCanBeRendered = new HashMap<>();

    private boolean isClean = false;

    @Override
    public <T extends IGLObject<?>, R extends IBaseNode<T>> R addNode(IGLObject<R> object) {
        //If provides
        return null;
    }

    @Override
    public Error validate() {
        for (IDemandingNode<?> demandingNode : requiredResourcesByID.values()){
            for (INodeID<?> resourceID : demandingNode.requires()){
                if (!providedResourcesByID.containsKey(resourceID)){
                    return new Error("Node " + demandingNode + " required " + resourceID + ", but no such resource was present.");
                }
            }
        }
        isClean = true;
        return Error.NONE;
    }

    @Override
    public void render(FrameBuffer fbo) {
        if(!isClean){
            Error validateErr = validate();
            if(validateErr != Error.NONE){
                throw new RuntimeException(validateErr.msg());
            }
        }


    }

}
