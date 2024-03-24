package gbw.melange.elements.problematic;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**
 * <p>ComputedShading class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ComputedShading {

    //TODO: Framebuffer size has to be adjusted to viewport resolution

    private final FrameBuffer frameBufferA = new FrameBuffer(Pixmap.Format.RGBA8888, 512, 512, true);
    private final FrameBuffer frameBufferB = new FrameBuffer(Pixmap.Format.RGBA8888, 512, 512, true);
    private final FrameBuffer frameBufferC = new FrameBuffer(Pixmap.Format.RGBA8888, 512, 512, true);
    FrameBuffer getFrameBufferA(){
        return frameBufferA;
    }
    FrameBuffer getFrameBufferB(){
        return frameBufferB;
    }
    FrameBuffer getFrameBufferC(){
        return frameBufferC;
    }
}
