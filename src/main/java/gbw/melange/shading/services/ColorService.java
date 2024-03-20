package gbw.melange.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.shaders.*;
import gbw.melange.shading.shaders.gradients.GradientFragmentBuilder;
import gbw.melange.shading.shaders.gradients.IGradientBuilder;
import gbw.melange.shading.shaders.partial.FragmentShader;
import gbw.melange.shading.shaders.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The implementation of the Api detailed by {@link Colors}
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class ColorService implements Colors {

    private static final Logger log = LogManager.getLogger();

    private static int nextId = 0;
    private final IShaderPipeline pipeline;

    @Autowired
    public ColorService(IShaderPipeline pipeline){
        this.pipeline = pipeline;
    }

    /** {@inheritDoc} */
    @Override
    public IWrappedShader<?> constant(Color color) {
        float r = color.r, g = color.g, b = color.b, a = color.a;
        IWrappedShader<?> wrapped = new BlindShader("CONSTANT_RGBA_"+(nextId++)+"("+r+","+g+","+b+","+a+")", VertexShader.DEFAULT, FragmentShader.constant(color));
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    /** {@inheritDoc} */
    @Override
    public ITexturedShader image(FileHandle src) throws IOException {
        return image(new Texture(src));
    }

    /** {@inheritDoc} */
    @Override
    public ITexturedShader image(Texture src) {
        ITexturedShader wrapped = new TextureShader("TEXTURE_"+(nextId++), VertexShader.DEFAULT, FragmentShader.TEXTURE);
        wrapped.bindResource((index, program) -> {
            src.bind(index);
            program.setUniformi(GLShaderAttr.TEXTURE.glValue(), index);
        }, src);
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder linearGradient() {
        return new GradientFragmentBuilder("GRADIENT_"+(nextId++), pipeline);
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder radialGradient() {
        //TODO: Implement a radial gradient builder
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public BlindShader fromFragment(FragmentShader fragmentShader) {
        BlindShader wrapped = new BlindShader("CUSTOM_FRAGMENT_"+(nextId++), VertexShader.DEFAULT, fragmentShader);
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }


}
