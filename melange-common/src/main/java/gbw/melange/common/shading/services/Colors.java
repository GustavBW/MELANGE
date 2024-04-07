package gbw.melange.common.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.generative.IBlindShader;
import gbw.melange.common.shading.generative.ITexturedShader;
import gbw.melange.common.shading.generative.checker.ICheckerBuilder;
import gbw.melange.common.shading.generative.noise.IPerlinFragmentBuilder;
import gbw.melange.common.shading.components.IFragmentShader;
import gbw.melange.common.shading.generative.gradients.IGradientBuilder;
import gbw.melange.common.shading.generative.voronoi.IVoronoiFragmentBuilder;
import gbw.melange.common.shading.postprocess.IBoxBlurShader;

/**
 * Spring-distributed entry point for the melange colors api.
 * Allows for quick and easy configuration of procedural shaders.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface Colors {

    IManagedShader<?> constant(Color color);
    ITexturedShader image(FileHandle src) throws Exception;
    ITexturedShader image(Texture src);
    IGradientBuilder linearGradient();
    IGradientBuilder radialGradient();
    ICheckerBuilder checker();
    IVoronoiFragmentBuilder voronoi();
    IPerlinFragmentBuilder perlin();

    IBlindShader fromFragment(IFragmentShader fragmentShader);

    //Maybe break post processes out into separate Effects api - although shader graphs will make that redundant
    IBoxBlurShader blur(int kernelSize);
}
