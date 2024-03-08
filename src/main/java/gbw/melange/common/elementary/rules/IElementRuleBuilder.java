package gbw.melange.common.elementary.rules;

import gbw.melange.common.builders.IBuilder;
import gbw.melange.rules.Rule;

public interface IElementRuleBuilder extends IBuilder<Rule> {
    IElementUserInteractionRuleBuilder clicked();
    IElementUserInteractionRuleBuilder mouseEnters();
    IElementUserInteractionRuleBuilder mouseLeaves();
    IElementUserInteractionRuleBuilder mouseDown();
    IElementUserInteractionRuleBuilder mouseUp();


}
