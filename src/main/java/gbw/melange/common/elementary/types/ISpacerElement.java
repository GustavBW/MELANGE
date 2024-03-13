package gbw.melange.common.elementary.types;

import gbw.melange.common.elementary.contraints.SizingPolicy;

/**
 * Represents empty space. If a nearby element has {@link SizingPolicy#FIT_CONTENT}, it will take priority in terms of space use.
 */
public interface ISpacerElement extends IConstrainedElement {
    double getRequestedHeight();
    double getRequestedWidth();
}
