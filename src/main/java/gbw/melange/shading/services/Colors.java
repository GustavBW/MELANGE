package gbw.melange.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.generative.BlindShader;
import gbw.melange.shading.generative.ITexturedShader;
import gbw.melange.shading.generative.checker.ICheckerBuilder;
import gbw.melange.shading.generative.checker.ICheckerShader;
import gbw.melange.shading.generative.noise.IPerlinFragmentBuilder;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.gradients.IGradientBuilder;
import gbw.melange.shading.generative.voronoi.IVoronoiFragmentBuilder;
import gbw.melange.shading.generative.voronoi.IVoronoiShader;

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

    BlindShader fromFragment(FragmentShader fragmentShader);
}
