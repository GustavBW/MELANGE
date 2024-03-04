package gbw.melange.common.elementary;

import gbw.melange.elements.ElementState;
import gbw.melange.elements.rules.IElementRuleBuilder;

public interface IElement {

    //Standard state getters
    IElementConstraints getConstraints();
    ElementState getState();

    //The spice
    IElementRuleBuilder when();

}
