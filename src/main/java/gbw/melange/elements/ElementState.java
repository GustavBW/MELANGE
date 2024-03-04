package gbw.melange.elements;

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
    STABLE;
}
