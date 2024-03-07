package gbw.melange.elements;


import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.IComputedTransforms;

public class ComputedTransforms implements IComputedTransforms {
    private Matrix4 matrix = new Matrix4();
    private Vector3 position = new Vector3();
    private Vector3 scale = new Vector3();
    private Quaternion rotation = new Quaternion();

    public ComputedTransforms(){}

    public void update(){
        matrix.getTranslation(position);
        matrix.getScale(scale);
        matrix.getRotation(rotation);
    }

    public Matrix4 getMatrix(){ //TODO: NO PUBLIC ACCESS!
        return matrix;
    }

    @Override
    public double getHeight() {
        return scale.y;
    }

    @Override
    public double getWidth() {
        return scale.x; //Since the mesh are in a -1 to 1 space, the scale is half the actual width
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
    public Quaternion getRotation() {
        return rotation;
    }
}
