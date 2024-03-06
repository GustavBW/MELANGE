package gbw.melange.common.elementary;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.elements.Element;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;

public interface IElement {

    IElement NULL = null; //TODO: Find a way to involve a neat null-object pattern here.

    //Standard state getters
    IElementConstraints getConstraints();
    ElementState getState();
    IElementStyleDefinition getStylings();
    Mesh getMesh();
    //The spice
    IElementRuleBuilder when();
    IElementRuleSet getRuleset();

}
