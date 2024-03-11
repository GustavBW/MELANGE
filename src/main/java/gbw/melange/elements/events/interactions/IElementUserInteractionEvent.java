package gbw.melange.elements.events.interactions;

import gbw.melange.elements.events.IElementEvent;
import gbw.melange.common.events.interactions.UserInteractionTypes;

public interface IElementUserInteractionEvent extends IElementEvent {
    UserInteractionTypes interactionType();
}
