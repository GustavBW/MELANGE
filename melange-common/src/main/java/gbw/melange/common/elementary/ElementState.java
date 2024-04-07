package gbw.melange.common.elementary;

/**
 * <p>ElementState class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum ElementState {
    /**
     * The content of this element is not quite ready, or the element itself isn't. Render a placeholder instead.
     */
    LOADING,
    /**
     * This element is currently not stable, it might be during a Cascading event or affected by one. <br/>
     * Regardless, it might not be safe to cause changes to this element right now.
     */
    VOLATILE,
    /**
     * This element can receive updates and changes without issue.
     */
    STABLE,
    /**
     * Something went wrong, usually a failed OnInit. Regardless, this element is not expected to ever work quite right, so, do what you must.
     */
    ERROR;
}
