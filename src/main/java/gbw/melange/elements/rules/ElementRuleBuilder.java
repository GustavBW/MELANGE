package gbw.melange.elements.rules;

import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder;
import gbw.melange.events.interactions.UserInteractionTypes;
import gbw.melange.rules.Rule;

public class ElementRuleBuilder implements IElementRuleBuilder {

    private final IElement originElement;

    public ElementRuleBuilder(IElement originElement){
        this.originElement = originElement;
    }

    @Override
    public Rule build() {
        return null;
    }

    //Interactions

    @Override
    public IElementUserInteractionRuleBuilder clicked() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_CLICK);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseEnters() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_ENTER);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseLeaves() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_EXIT);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseDown() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_DOWN);
    }

    @Override
    public IElementUserInteractionRuleBuilder mouseUp() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_UP);
    }


}
