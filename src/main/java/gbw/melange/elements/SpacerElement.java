package gbw.melange.elements;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.MeshTable;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.types.ISpacerElement;
import gbw.melange.elements.constraints.ElementConstraints;

public class SpacerElement implements ISpacerElement {
    private final Mesh mesh = MeshTable.SQUARE.getMesh();
    private final IElementConstraints constraints;
    private double width = 1;
    private double height = 1;

    public SpacerElement(IReferenceConstraintDefinition constraints, double width, double height) {
        this.constraints = new ElementConstraints(constraints);
        this.width = width;
        this.height = height;
    }
    @Override
    public Mesh getMesh(){
        return mesh;
    }
    @Override
    public IElementConstraints getConstraints(){
        return constraints;
    }

    @Override
    public double getRequestedHeight() {
        return height;
    }

    @Override
    public double getRequestedWidth() {
        return width;
    }

    @Override
    public void dispose() {
        mesh.dispose();
    }
}