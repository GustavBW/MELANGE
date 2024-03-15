package gbw.melange.shading;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.errors.ShaderCompilationIssue;
import gbw.melange.shading.procedural.gradients.GradientFragmentShaderBuilder;
import gbw.melange.shading.procedural.gradients.IGradientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * The implementation of the Api detailed by {@link Colors}
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class ColorService implements Colors {

    private static final Logger log = LoggerFactory.getLogger(ColorService.class);

    private static int nextId = 0;
    private final IShaderPipeline pipeline;

    @Autowired
    public ColorService(IShaderPipeline pipeline){
        this.pipeline = pipeline;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader constant(Color color) {
        float r = color.r, g = color.g, b = color.b, a = color.a;
        IWrappedShader wrapped = new WrappedShader("CONSTANT_RGBA_"+(nextId++)+"("+r+","+g+","+b+","+a+")", VertexShader.DEFAULT, FragmentShader.constant(color));
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader image(FileHandle src) throws IOException {
        return image(new Texture(src));
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader image(Texture src) {
        IWrappedShader wrapped = new WrappedShader("TEXTURE_"+(nextId++), VertexShader.DEFAULT, FragmentShader.TEXTURE);
        wrapped.bindResource((index, program) -> {
            src.bind(index);
            program.setUniformi("u_texture", index);
        }, List.of(src));
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder linearGradient() {
        return new GradientFragmentShaderBuilder("GRADIENT_"+(nextId++), pipeline);
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder radialGradient() {
        //TODO: Implement a radial gradient builder
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader fromFragment(FragmentShader fragmentShader) {
        IWrappedShader wrapped = new WrappedShader("CUSTOM_FRAGMENT_"+(nextId++), VertexShader.DEFAULT, fragmentShader);
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }


}
