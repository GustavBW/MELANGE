package gbw.melange.elements.rules;

import gbw.melange.common.elementary.IElement;
import gbw.melange.events.interactions.UserInteractionTypes;

public class ElementRuleBuilder implements IElementRuleBuilder {

    private final IElement originElement;

    public ElementRuleBuilder(IElement originElement){
        this.originElement = originElement;
    }


    //Interactions

    @Override
    public IElementUserInteractionRuleBuilder clicked() {
        return new ElementUserInteractionRuleBuilder(originElement, UserInteractionTypes.MOUSE_CLICK);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseEnters() {
        return new ElementUserInteractionRuleBuilder(originElement, UserInteractionTypes.MOUSE_ENTER);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseLeaves() {
        return new ElementUserInteractionRuleBuilder(originElement, UserInteractionTypes.MOUSE_EXIT);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseDown() {
        return new ElementUserInteractionRuleBuilder(originElement, UserInteractionTypes.MOUSE_DOWN);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseUp() {
        return new ElementUserInteractionRuleBuilder(originElement, UserInteractionTypes.MOUSE_UP);
    }
}
