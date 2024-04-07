package gbw.melange.elements.problematic;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IComputedTransforms;
import gbw.melange.common.elementary.contraints.IElementConstraints;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.elements.constraints.ElementConstraints;
import gbw.melange.elements.rules.ElementRuleBuilder;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;
import gbw.melange.elements.rules.ElementRuleSet;
import gbw.melange.elements.styling.ElementStyleDefinition;
import gbw.melange.common.mesh.IManagedMesh;

//TODO: Move to abstract reference definition pipeline for delayed instantiation?
/**
 * <p>Abstract Element class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public abstract class Element<T> implements IElement<T> {

    private final IElementConstraints constraints;
    //An element is initially considered volatile until its constraints have resolved.
    private ElementState state = ElementState.VOLATILE;
    private final IElementStyleDefinition styling;
    private final IElementRuleSet ruleset = new ElementRuleSet();
    private final IComputedTransforms computed = new ComputedTransforms();
    private final ComputedShading computedShading = new ComputedShading();
    private final IManagedMesh meshWrapper;

    protected Element(IManagedMesh meshWrapper, IReferenceStyleDefinition styling, IReferenceConstraintDefinition constraints){
        this.constraints = new ElementConstraints(constraints);
        this.styling = new ElementStyleDefinition(styling);
        this.meshWrapper = meshWrapper;
    }

    /**
     * <p>when.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementRuleBuilder} object
     */
    public IElementRuleBuilder when(){
        return new ElementRuleBuilder(this);
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraints getConstraints() {
        return constraints;
    }

    /** {@inheritDoc} */
    @Override
    public ElementState getState() {
        return state;
    }
    /**
     * <p>Setter for the field <code>state</code>.</p>
     *
     * @param state a {@link gbw.melange.common.elementary.ElementState} object
     */
    protected void setState(ElementState state){
        this.state = state;
    }

    /** {@inheritDoc} */
    @Override
    public IElementStyleDefinition getStylings() {
        return styling;
    }

    /** {@inheritDoc} */
    @Override
    public Mesh getMesh() {
        return meshWrapper.getMesh();
    }

    /** {@inheritDoc} */
    @Override
    public IElementRuleSet getRuleset(){
        return ruleset;
    }
    /** {@inheritDoc} */
    @Override
    public void dispose(){
        styling.dispose();
        meshWrapper.dispose();
    }
    /** {@inheritDoc} */
    @Override
    public IComputedTransforms computed(){
        return computed;
    }

    private static int elementIdTracker = 0;
    private final int id = elementIdTracker++;
    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int compareTo(IElement<?> element){
        if(element == null) return 1;
        if(element == this) return 0;
        if(element.getConstraints().getAttachedTo() == this) return -1;
        return 1;
    }

    @Override
    public void onViewportResize(double x0, double y0, double x1, double y1){
        throw new RuntimeException("impl this");
    }
}
