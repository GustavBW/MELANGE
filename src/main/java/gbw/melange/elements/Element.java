package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.MeshTable;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.IElementConstraints;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.elements.rules.IElementRuleBuilder;
import gbw.melange.elements.styling.IElementStyleDefinition;

public class Element implements IElement {

    private ElementConstraints constrains;
    //An element is initially considered volatile until its constraints have resolved.
    private ElementState state = ElementState.VOLATILE;
    private IElementStyleDefinition styling = IElementStyleDefinition.DEFAULT;
    private Mesh mesh = MeshTable.SQUARE.getMesh();
    private ISpace space;
    public Element(ISpace initialParentSpace){
        this.space = initialParentSpace;
    }

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

    @Override
    public IElementStyleDefinition getStylings() {
        return styling;
    }

    @Override
    public Mesh getMesh() {
        return mesh;
    }


}
