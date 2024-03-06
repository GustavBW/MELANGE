package gbw.melange.elements.rules;

import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder;
import gbw.melange.events.interactions.UserInteractionTypes;

public class ElementUserInteractionRuleBuilder implements IElementUserInteractionRuleBuilder {

    private UserInteractionTypes interactionType = UserInteractionTypes.MOUSE_CLICK;
    private final IElement originElement;

    public ElementUserInteractionRuleBuilder(IElement originElement, UserInteractionTypes interactionType){
        this.interactionType = interactionType;
        this.originElement = originElement;
    }



}
