package gbw.melange.common.elementary.contraints;

public enum SizingPolicy {
    /**
     * Take up all available space in the element this is attached to
     */
    FILL_PARENT,
    /**
     * Take up only as much as is required for the content of this element.
     * This is contained within the element, the element with this sizing policy, is attached to.
     * If that too declares its sizing policy as {@link SizingPolicy#FIT_CONTENT} the behaviour will propagate upwards one layer more.
     */
    FIT_CONTENT;
}
