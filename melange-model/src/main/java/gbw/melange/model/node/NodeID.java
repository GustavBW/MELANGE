package gbw.melange.model.node;

import gbw.melange.model.SourceType;
import gbw.melange.model.typealias.IGLAlias;

public abstract class NodeID<T> implements INodeID<T> {

    private NodeID(){

    }

    public Class<T> clazz(){
        return null;
    }
    public SourceType sourceOf(){
        return null;
    }
    public IGLAlias alsoKnownAs(){
        return null;
    }
}
