package gbw.melange.model.node;

import gbw.melange.model.SourceType;
import gbw.melange.model.typealias.IGLAlias;

import java.util.Objects;

/**
 * Hashkey for internal lookup Hashtable
 * A HashMap uses the Object.hashCode() so the INodeID class should overwrite that
 * @param <T>
 */
public abstract class INodeID<T> {
    public abstract Class<T> clazz();
    public abstract SourceType sourceOf();
    public abstract IGLAlias alsoKnownAs();
    @Override
    public int hashCode(){
        return Objects.hash(clazz(), sourceOf(), alsoKnownAs());
    }


    public static final Class<?> ANY_CLASS = Void.class;
    public static final SourceType ANY_SOURCE = SourceType.NONE;
    public static final IGLAlias ANY_ALIAS = IGLAlias.NONE;

}
