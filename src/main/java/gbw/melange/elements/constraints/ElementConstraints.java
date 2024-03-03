package gbw.melange.elements.constraints;

/**
 * Physical relations between a given Element and another.
 * A Child will always render on top of a parent
 */
public class ElementConstraints implements IElementConstraints {

    private ElementAnchoring anchorOnParent = ElementAnchoring.TOP_LEFT;
    private ElementAnchoring anchorOnSelf = ElementAnchoring.TOP_LEFT;


}
