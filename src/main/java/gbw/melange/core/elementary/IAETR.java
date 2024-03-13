package gbw.melange.core.elementary;

import gbw.melange.common.elementary.types.IConstrainedElement;

import java.util.List;

/**
 * I-Automatic-Element-Transform-Resolver
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IAETR {
    /**
     * <p>load.</p>
     *
     * @param elements a {@link java.util.List} object
     */
    void load(List<? extends IConstrainedElement> elements);
    /**
     * <p>resolve.</p>
     */
    void resolve();
    /**
     * <p>resolveFrom.</p>
     *
     * @param element a {@link gbw.melange.common.elementary.types.IConstrainedElement} object
     */
    void resolveFrom(IConstrainedElement element);
}
