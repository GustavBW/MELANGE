package gbw.melange.common.builders;

/**
 * <p>IBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IBuilder<T> {

    /**
     * <p>build.</p>
     *
     * @return A new instance.
     */
    T build();
}
