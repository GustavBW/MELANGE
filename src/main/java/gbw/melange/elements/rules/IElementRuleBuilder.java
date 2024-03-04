package gbw.melange.elements.rules;

public interface IElementRuleBuilder {
    IElementUserInteractionRuleBuilder clicked();
    IElementUserInteractionRuleBuilder mouseEnters();
    IElementUserInteractionRuleBuilder mouseLeaves();
    IElementUserInteractionRuleBuilder mouseDown();
    IElementUserInteractionRuleBuilder mouseUp();


}
