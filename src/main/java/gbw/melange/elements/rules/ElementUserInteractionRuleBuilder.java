package gbw.melange.elements.rules;

import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder;
import gbw.melange.common.events.interactions.UserInteractionTypes;

/**
 * <p>ElementUserInteractionRuleBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementUserInteractionRuleBuilder implements IElementUserInteractionRuleBuilder {

    private final UserInteractionTypes interactionType;
    private final IElement<?> originElement;
    private final IElementRuleBuilder parentBuilder;

    /**
     * <p>Constructor for ElementUserInteractionRuleBuilder.</p>
     *
     * @param parentBuilder a {@link gbw.melange.common.elementary.rules.IElementRuleBuilder} object
     * @param originElement a {@link gbw.melange.common.elementary.types.IElement} object
     * @param interactionType a {@link gbw.melange.common.events.interactions.UserInteractionTypes} object
     */
    public ElementUserInteractionRuleBuilder(IElementRuleBuilder parentBuilder, IElement<?> originElement, UserInteractionTypes interactionType){
        this.interactionType = interactionType;
        this.originElement = originElement;
        this.parentBuilder = parentBuilder;
    }


    /** {@inheritDoc} */
    @Override
    public IElementRuleBuilder apply() {
        return parentBuilder;
    }
}
