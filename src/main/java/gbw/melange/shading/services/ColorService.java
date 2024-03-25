package gbw.melange.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.*;
import gbw.melange.shading.generative.checker.CheckerFragmentBuilder;
import gbw.melange.shading.generative.checker.ICheckerBuilder;
import gbw.melange.shading.generative.gradients.GradientFragmentBuilder;
import gbw.melange.shading.generative.gradients.IGradientBuilder;
import gbw.melange.shading.generative.noise.IPerlinFragmentBuilder;
import gbw.melange.shading.generative.noise.PerlinFragmentBuilder;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import gbw.melange.shading.generative.voronoi.IVoronoiFragmentBuilder;
import gbw.melange.shading.generative.voronoi.VoronoiFragmentBuilder;
import gbw.melange.shading.postprocessing.BoxBlurShader;
import gbw.melange.shading.postprocessing.IBoxBlurShader;
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
    public IManagedShader<?> constant(Color color) {
        float r = color.r, g = color.g, b = color.b, a = color.a;
        IManagedShader<?> wrapped = new BlindShader(
                generateId("CONSTANT_RGBA("+r+","+g+","+b+","+a+")"),
                VertexShader.DEFAULT,
                FragmentShader.constant(color),
                ShaderClassification.SIMPLE, true);
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
        ITexturedShader wrapped = new TextureShader(generateId("TEXTURE_"), VertexShader.DEFAULT, FragmentShader.TEXTURE);
        wrapped.setTexture(src, GLShaderAttr.TEXTURE.glValue());
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder linearGradient() {
        return new GradientFragmentBuilder(generateId("GRADIENT_"), pipeline);
    }

    /** {@inheritDoc} */
    @Override
    public IGradientBuilder radialGradient() {
        //TODO: Implement a radial gradient builder
        return null;
    }

    @Override
    public ICheckerBuilder checker() {
        return new CheckerFragmentBuilder(generateId("CHECKER_"), pipeline);
    }

    @Override
    public IVoronoiFragmentBuilder voronoi() {
        return new VoronoiFragmentBuilder(generateId("VORONOI_"), pipeline);
    }

    @Override
    public IPerlinFragmentBuilder perlin() {
        return new PerlinFragmentBuilder(generateId("PERLIN_"), pipeline);
    }

    /** {@inheritDoc} */
    @Override
    public BlindShader fromFragment(FragmentShader fragmentShader) {
        BlindShader wrapped = new BlindShader(generateId("CUSTOM_FRAGMENT_"), VertexShader.DEFAULT, fragmentShader);
        pipeline.registerForCompilation(wrapped);
        return wrapped;
    }

    @Override
    public IBoxBlurShader blur(int kernelSize) {
        IBoxBlurShader shader = new BoxBlurShader(generateId("BOX_BLUR_"), kernelSize);
        pipeline.registerForCompilation(shader);
        return shader;
    }

    private String generateId(String base){
        return base + (nextId++);
    }

}
