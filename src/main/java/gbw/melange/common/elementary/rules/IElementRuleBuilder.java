package gbw.melange.common.elementary.rules;

public interface IElementRuleBuilder {
    IElementUserInteractionRuleBuilder clicked();
    IElementUserInteractionRuleBuilder mouseEnters();
    IElementUserInteractionRuleBuilder mouseLeaves();
    IElementUserInteractionRuleBuilder mouseDown();
    IElementUserInteractionRuleBuilder mouseUp();


}
