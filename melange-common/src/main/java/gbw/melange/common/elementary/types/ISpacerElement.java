package gbw.melange.common.elementary.types;

/**
 * Represents empty space. If a nearby element has {@link gbw.melange.common.elementary.contraints.SizingPolicy#FIT_CONTENT}, it will take priority in terms of space use.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface ISpacerElement extends IConstrainedElement {
    /**
     * <p>getRequestedHeight.</p>
     *
     * @return a double
     */
    double getRequestedHeight();
    /**
     * <p>getRequestedWidth.</p>
     *
     * @return a double
     */
    double getRequestedWidth();
}
