package gbw.melange.common.elementary.contraints;

/**
 * In relation to the extends and mesh of the element this element is attached to.
 */
public enum ElementAnchoring {
    CENTER(.5, .5),
    TOP(.5,1),
    TOP_RIGHT(1,1),
    RIGHT(1,.5),
    BOTTOM_RIGHT(1,0),
    BOTTOM(.5, 0),
    BOTTOM_LEFT(0,0),
    LEFT(0, .5),
    TOP_LEFT(0,1);

    /**
     * Representative directions in terms of a vector for which each component is in the range 0-1.
     * {.5, .5} is the center, {0, 0} is bottom left, {1, 1} is top right.
     */
    public final Anchor anchor;
    ElementAnchoring(double x, double y){
        assert x > 0 && x <= 1 && y > 0 && y <= 1;
        this.anchor = new Anchor(x, y);
    }
}
