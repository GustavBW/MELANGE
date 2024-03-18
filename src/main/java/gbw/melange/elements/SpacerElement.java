package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.constants.MeshTable;
import gbw.melange.common.elementary.contraints.IComputedTransforms;
import gbw.melange.common.elementary.contraints.IElementConstraints;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.types.ISpacerElement;
import gbw.melange.elements.constraints.ElementConstraints;

/**
 * <p>SpacerElement class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class SpacerElement implements ISpacerElement {
    private final Mesh mesh = MeshTable.SQUARE.getMesh();
    private final IElementConstraints constraints;
    private final IComputedTransforms computed = new ComputedTransforms();
    private double width = 1;
    private double height = 1;

    /**
     * <p>Constructor for SpacerElement.</p>
     *
     * @param constraints a {@link gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition} object
     * @param width a double
     * @param height a double
     */
    public SpacerElement(IReferenceConstraintDefinition constraints, double width, double height) {
        this.constraints = new ElementConstraints(constraints);
        this.width = width;
        this.height = height;
    }
    /** {@inheritDoc} */
    @Override
    public Mesh getMesh(){
        return mesh;
    }

    /** {@inheritDoc} */
    @Override
    public IComputedTransforms computed() {
        return computed;
    }

    /** {@inheritDoc} */
    @Override
    public IElementConstraints getConstraints(){
        return constraints;
    }

    /** {@inheritDoc} */
    @Override
    public double getRequestedHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public double getRequestedWidth() {
        return width;
    }

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        mesh.dispose();
    }
}
