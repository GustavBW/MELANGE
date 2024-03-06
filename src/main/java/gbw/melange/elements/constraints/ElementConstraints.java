package gbw.melange.elements.constraints;

import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.elementary.IElementConstraints;

/**
 * Physical relations between a given Element and another.
 * A Child will always render on top of a parent
 */
public class ElementConstraints implements IElementConstraints {

    /**
     * Cascading <br/>
     * What part of this element anchors to the parents self anchor
     */
    private ElementAnchoring attachedAnchor = ElementAnchoring.TOP_LEFT;

    /**
     * Cascading <br/>
     * Where on this element, other elements attach
     */
    private ElementAnchoring selfAnchor = ElementAnchoring.TOP_LEFT;
    //Contained change
    private ElementSizing sizingPolicy = ElementSizing.FILL_PARENT;



}
