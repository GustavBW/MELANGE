package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.MeshTable;
import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementState;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.common.elementary.IElementStyleDefinition;
import gbw.melange.elements.rules.ElementRuleSet;

public abstract class Element implements IElement {

    private IElementConstraints constraints = new ElementConstraints();
    //An element is initially considered volatile until its constraints have resolved.
    private ElementState state = ElementState.VOLATILE;
    private IElementStyleDefinition styling = IElementStyleDefinition.DEFAULT;
    private IElementRuleSet ruleset = new ElementRuleSet();
    private Mesh mesh = MeshTable.SQUARE.getMesh();
    private IElement attachedTo;

    Element(IElement attachedTo, IElementStyleDefinition styling, IElementConstraints constraints){
        this.constraints = constraints;
        this.attachedTo = attachedTo;
        this.styling = styling;
    }

    public IElementRuleBuilder when(){
        return new ElementRuleBuilder(this);
    }

    @Override
    public IElementConstraints getConstraints() {
        return constraints;
    }

    @Override
    public ElementState getState() {
        return state;
    }

    @Override
    public IElementStyleDefinition getStylings() {
        return styling;
    }

    @Override
    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public IElementRuleSet getRuleset(){
        return ruleset;
    }


}
