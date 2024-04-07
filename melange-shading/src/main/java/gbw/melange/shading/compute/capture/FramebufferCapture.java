package gbw.melange.shading.compute.capture;

import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;

public class FramebufferCapture implements ICaptureMethod<GLFrameBuffer> {
    private GLFrameBuffer result;

    @Override
    public void setup() {

    }

    @Override
    public void execute() {
        result.bind();


    }

    @Override
    public GLFrameBuffer getResult() {
        return result;
    }
}