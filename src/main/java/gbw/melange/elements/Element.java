package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.elements.rules.IElementRuleBuilder;

public class Element implements IElement {

    private Mesh mesh;
    private ElementConstraints constrains;
    //An element is initially concidered volatile until its contrains have resolved.
    private ElementState state = ElementState.VOLATILE;

    public IElementRuleBuilder when(){
        return new ElementRuleBuilder(this);
    }

    @Override
    public IElementConstraints getConstraints() {
        return constrains;
    }

    @Override
    public ElementState getState() {
        return state;
    }


}
