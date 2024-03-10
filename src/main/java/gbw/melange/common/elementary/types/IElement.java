package gbw.melange.common.elementary.types;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.ElementState;
import gbw.melange.common.elementary.IComputedTransforms;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;

public interface IElement extends Disposable {

    IElement NULL = null; //TODO: Find a way to involve a neat null-object pattern here.

    //Standard state getters
    IElementConstraints getConstraints();
    ElementState getState();
    IElementStyleDefinition getStylings();
    Mesh getMesh();
    IComputedTransforms computed();
    int getId();
    //The spice
    IElementRuleBuilder when();
    IElementRuleSet getRuleset();

}
