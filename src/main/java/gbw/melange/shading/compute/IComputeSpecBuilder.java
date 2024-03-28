package gbw.melange.shading.compute;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;

import java.util.function.Function;

public interface IComputeSpecBuilder<T> {

    void setSerializer(Function<GLFrameBuffer.FloatFrameBufferBuilder, T> serializer);



}
