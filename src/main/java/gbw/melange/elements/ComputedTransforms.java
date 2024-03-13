package gbw.melange.elements;


import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.contraints.IComputedTransforms;

public class ComputedTransforms implements IComputedTransforms {
    private Matrix4 matrix = new Matrix4();
    private final Vector3 position = new Vector3();
    private final Vector3 scale = new Vector3();
    private final Quaternion rotation = new Quaternion();



    public ComputedTransforms(){}

    public void update(){
        matrix.getTranslation(position);
        matrix.getScale(scale);
        matrix.getRotation(rotation);
    }

    @Override
    public double getHeight() {
        return scale.y;
    }

    @Override
    public double getWidth() {
        return scale.x; //Since the mesh are in a -1 to 1 space, I think the scale is half the actual width, but I don't know before automatic element transform resolution have been implemented
    }

    @Override
    public double getPositionX() {
        return position.x;
    }

    @Override
    public double getPositionY() {
        return position.y;
    }


    @Override
    public double[] getAxisAlignedBounds(){
        return new double[]{position.x, position.y, scale.x, scale.y};
    }

    @Override
    public Quaternion getRotation() {
        return rotation;
    }


    //These are package private and not declared in the IComputedTransforms interface, as noone but the IElementRenderer implementation should have access to them
    Matrix4 getMatrix(){
        return matrix;
    }


}
