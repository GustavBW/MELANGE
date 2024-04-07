package gbw.melange.common.builders;

/**
 * <p>IPartialBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IPartialBuilder<T extends IBuilder<?>> {
    /**
     * Applies the sub-configuration to the parent builder, and returns the parent builder.
     *
     * @return a T object
     */
    T apply();
}
