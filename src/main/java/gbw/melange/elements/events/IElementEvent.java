package gbw.melange.elements.events;

import gbw.melange.common.elementary.types.IElement;

/**
 * <p>IElementEvent interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementEvent {
    /**
     * What element this event happened for
     *
     * @return a {@link gbw.melange.common.elementary.types.IElement} object
     */
    IElement<?> element();

    /**
     * The characteristic of the moment that caused this event to be fired.
     *
     * @return a {@link gbw.melange.elements.events.ElementEventType} object
     */
    ElementEventType type();
}
