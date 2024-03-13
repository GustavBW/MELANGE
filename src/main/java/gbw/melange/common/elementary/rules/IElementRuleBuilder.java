package gbw.melange.common.elementary.rules;

import gbw.melange.common.builders.IBuilder;
import gbw.melange.rules.Rule;

/**
 * <p>IElementRuleBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementRuleBuilder extends IBuilder<Rule> {
    /**
     * <p>clicked.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder} object
     */
    IElementUserInteractionRuleBuilder clicked();
    /**
     * <p>mouseEnters.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder} object
     */
    IElementUserInteractionRuleBuilder mouseEnters();
    /**
     * <p>mouseLeaves.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder} object
     */
    IElementUserInteractionRuleBuilder mouseLeaves();
    /**
     * <p>mouseDown.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder} object
     */
    IElementUserInteractionRuleBuilder mouseDown();
    /**
     * <p>mouseUp.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder} object
     */
    IElementUserInteractionRuleBuilder mouseUp();


}
