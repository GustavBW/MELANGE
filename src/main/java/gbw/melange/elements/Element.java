package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.elements.rules.ElementRuleSet;
import gbw.melange.elements.styling.ElementStyleDefinition;

import java.util.UUID;
//TODO: Move to abstract reference definition pipeline?
public abstract class Element<T> implements IElement<T> {

    private IElementConstraints constraints;
    //An element is initially considered volatile until its constraints have resolved.
    private ElementState state = ElementState.VOLATILE;
    private IElementStyleDefinition styling;
    private IElementRuleSet ruleset = new ElementRuleSet();
    private final IComputedTransforms computed = new ComputedTransforms();
    private final ComputedShading computedShading = new ComputedShading();
    /**
     * All vertex parameters (x, y, z) are always within a -1 to 1 space.
     */
    private Mesh mesh;

    Element(Mesh mesh, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints){
        this.constraints = new ElementConstraints(constraints);
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
    protected void setState(ElementState state){
        this.state = state;
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
    @Override
    public IComputedTransforms computed(){
        return computed;
    }

    private static int elementIdTracker = 0;
    private final int id = elementIdTracker++;
    @Override
    public int getId(){
        return id;
    }
    static int nextIdInSequence(){
        return elementIdTracker + 1;
    }
    ComputedShading getComputedShading(){
        return computedShading;
    }

    @Override
    public int compareTo(IElement<?> element){
        if(element == null) return 1;
        if(element == this) return 0;
        if(element.getConstraints().getAttachedTo() == this) return -1;
        return 1;
    }
}
