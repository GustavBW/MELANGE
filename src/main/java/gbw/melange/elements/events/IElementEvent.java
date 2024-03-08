package gbw.melange.elements.events;

import gbw.melange.common.elementary.types.IElement;

public interface IElementEvent {
    /**
     * What element this event happened for
     */
    IElement element();

    /**
     * The characteristic of the moment that caused this event to be fired.
     */
    ElementEventType type();
}
