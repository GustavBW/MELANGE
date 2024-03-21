package gbw.melange.shading.services;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.generative.BlindShader;
import gbw.melange.shading.generative.ITexturedShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.gradients.IGradientBuilder;
import gbw.melange.shading.generative.IWrappedShader;

/**
 * Spring-distributed entry point for the melange colors api.
 * Allows for quick and easy configuration of procedural shaders.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface Colors {
    /**
     * <p>constant.</p>
     *
     * @param color a {@link com.badlogic.gdx.graphics.Color} object
     * @return a {@link IWrappedShader} object
     */
    IWrappedShader<?> constant(Color color);

    /**
     * <p>image.</p>
     *
     * @throws java.lang.Exception if the file couldn't be found.
     * @param src a {@link com.badlogic.gdx.files.FileHandle} object
     * @return a {@link IWrappedShader} object
     */
    ITexturedShader image(FileHandle src) throws Exception;
    /**
     * <p>image.</p>
     *
     * @param src a {@link com.badlogic.gdx.graphics.Texture} object
     * @return a {@link IWrappedShader} object
     */
    ITexturedShader image(Texture src);

    /**
     * <p>linearGradient.</p>
     *
     * @return a {@link IGradientBuilder} object
     */
    IGradientBuilder linearGradient();
    /**
     * <p>radialGradient.</p>
     *
     * @return a {@link IGradientBuilder} object
     */
    IGradientBuilder radialGradient();

    /**
     * <p>fromFragment.</p>
     *
     * @param fragmentShader a {@link FragmentShader} object
     * @return a {@link IWrappedShader} object
     */
    BlindShader fromFragment(FragmentShader fragmentShader);
}
