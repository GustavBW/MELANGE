package gbw.melange.model.node;

import gbw.melange.model.SourceType;
import gbw.melange.model.typealias.IGLAlias;

/**
 * Hashkey for internal lookup Hashtable
 * A HashMap uses the Object.hashCode() so the INodeID class should overwrite that
 * @param <T>
 */
public interface INodeID<T> {
    Class<T> clazz();
    SourceType sourceOf();
    IGLAlias alsoKnownAs();
}
