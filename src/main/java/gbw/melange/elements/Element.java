package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.MeshTable;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.constraints.ReferenceConstraintDefinition;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.elements.rules.ElementRuleSet;
import gbw.melange.elements.styling.ElementStyleDefinition;
import gbw.melange.elements.styling.ReferenceStyleDefinition;

public abstract class Element implements IElement {

    private IElementConstraints constraints;
    //An element is initially considered volatile until its constraints have resolved.
    private ElementState state = ElementState.VOLATILE;
    private IElementStyleDefinition styling;
    private IElementRuleSet ruleset = new ElementRuleSet();
    private Mesh mesh;
    private IElement attachedTo;

    Element(Mesh mesh, IElement attachedTo, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints){
        this.constraints = new ElementConstraints(constraints);
        this.attachedTo = attachedTo;
        this.styling = new ElementStyleDefinition(styling);
        this.mesh = mesh;
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
    @Override
    public void dispose(){
        styling.dispose();
        mesh.dispose();
    }


}
