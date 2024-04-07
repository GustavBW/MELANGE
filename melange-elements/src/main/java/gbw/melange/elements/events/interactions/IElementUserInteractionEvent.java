package gbw.melange.elements.events.interactions;

import gbw.melange.elements.events.IElementEvent;
import gbw.melange.common.events.interactions.UserInteractionTypes;

/**
 * <p>IElementUserInteractionEvent interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementUserInteractionEvent extends IElementEvent {
    /**
     * <p>interactionType.</p>
     *
     * @return a {@link gbw.melange.common.events.interactions.UserInteractionTypes} object
     */
    UserInteractionTypes interactionType();
}
