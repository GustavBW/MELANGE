package gbw.melange.elements;

import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.constraints.IElementConstraints;

public interface IElement {

    int getZOrdering();

    IElementConstraints getConstraints();

}
