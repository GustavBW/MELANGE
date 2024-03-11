package gbw.melange.elements;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.IComputedTransforms;

public class ComputedTransforms implements IComputedTransforms {
    private final Matrix4 matrix = new Matrix4();
    private final Vector3 position = new Vector3();
    private final Vector3 scale = new Vector3();
    private final Quaternion rotation = new Quaternion();

    //TODO: Framebuffer size has to be adjusted to viewport resolution
    private final FrameBuffer frameBufferA = new FrameBuffer(Pixmap.Format.RGBA8888, 256, 256, true);
    private final FrameBuffer frameBufferB = new FrameBuffer(Pixmap.Format.RGBA8888, 256, 256, true);

    public ComputedTransforms(){}

    public void update(){
        matrix.getTranslation(position);
        matrix.getScale(scale);
        matrix.getRotation(rotation);
    }

    public void setScale(double x, double y, double z){ //TODO: REMOVE THIS
        matrix.setToScaling((float) x,(float) y,(float) z);
    }
    public void setTranslation(double x, double y, double z){ //TODO: REMOVE THIS
        matrix.setTranslation((float) x, (float) y, (float) z);
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


    //These are package private and not declared in the IComputedTransforms interface, as noone but the IElementRenderer implementation should have access to them
    Matrix4 getMatrix(){
        return matrix;
    }
    FrameBuffer getFrameBufferA(){
        return frameBufferA;
    }
    FrameBuffer getFrameBufferB(){
        return frameBufferB;
    }
}
