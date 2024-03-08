package gbw.melange.elements.rules;

import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder;
import gbw.melange.events.interactions.UserInteractionTypes;

public class ElementUserInteractionRuleBuilder implements IElementUserInteractionRuleBuilder {

    private UserInteractionTypes interactionType = UserInteractionTypes.MOUSE_CLICK;
    private final IElement originElement;
    private final IElementRuleBuilder parentBuilder;

    public ElementUserInteractionRuleBuilder(IElementRuleBuilder parentBuilder, IElement originElement, UserInteractionTypes interactionType){
        this.interactionType = interactionType;
        this.originElement = originElement;
        this.parentBuilder = parentBuilder;
    }


    @Override
    public IElementRuleBuilder apply() {
        return parentBuilder;
    }
}
