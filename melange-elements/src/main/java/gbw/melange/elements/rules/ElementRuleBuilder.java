package gbw.melange.elements.rules;

import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementUserInteractionRuleBuilder;
import gbw.melange.common.events.interactions.UserInteractionTypes;
import gbw.melange.rules.Rule;

/**
 * <p>ElementRuleBuilder class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ElementRuleBuilder implements IElementRuleBuilder {

    private final IElement<?> originElement;

    /**
     * <p>Constructor for ElementRuleBuilder.</p>
     *
     * @param originElement a {@link gbw.melange.common.elementary.types.IElement} object
     */
    public ElementRuleBuilder(IElement<?> originElement){
        this.originElement = originElement;
    }

    /** {@inheritDoc} */
    @Override
    public Rule build() {
        return null;
    }

    //Interactions

    /** {@inheritDoc} */
    @Override
    public IElementUserInteractionRuleBuilder clicked() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_CLICK);
    }

    /** {@inheritDoc} */
    @Override
    public IElementUserInteractionRuleBuilder mouseEnters() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_ENTER);
    }

    /** {@inheritDoc} */
    @Override
    public IElementUserInteractionRuleBuilder mouseLeaves() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_EXIT);
    }

    /** {@inheritDoc} */
    @Override
    public IElementUserInteractionRuleBuilder mouseDown() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_DOWN);
    }

    /** {@inheritDoc} */
    @Override
    public IElementUserInteractionRuleBuilder mouseUp() {
        return new ElementUserInteractionRuleBuilder(this, originElement, UserInteractionTypes.MOUSE_UP);
    }


}
