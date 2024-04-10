package gbw.melange.model.node;

import gbw.melange.model.SourceType;
import gbw.melange.model.typealias.IGLAlias;

import java.util.List;
import java.util.Set;

public interface NodeID<T> {

    Class<T> clazz();
    Set<SourceType> sourceOf();
    Set<IGLAlias> sourceAliasses();
    void bind(IGLAlias alias);

}
