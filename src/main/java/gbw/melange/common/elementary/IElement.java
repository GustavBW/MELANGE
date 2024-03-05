package gbw.melange.common.elementary;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import gbw.melange.elements.ElementState;
import gbw.melange.elements.rules.IElementRuleBuilder;
import gbw.melange.elements.styling.IElementStyleDefinition;

public interface IElement {

    //Standard state getters
    IElementConstraints getConstraints();
    ElementState getState();
    IElementStyleDefinition getStylings();

    Mesh getMesh();

    //The spice
    IElementRuleBuilder when();

}
