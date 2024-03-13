package gbw.melange.shading;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import gbw.melange.shading.templating.gradients.IGradientBuilder;

import java.io.IOException;

/**
 * Spring-distributed entry point for shader pipeline
 */
public interface Colors {
    IWrappedShader constant(Color color);

    /**
     * @throws Exception if the file couldn't be found.
     */
    IWrappedShader image(FileHandle src) throws Exception;
    IWrappedShader image(Texture src);

    IGradientBuilder linearGradient();
    IGradientBuilder radialGradient();

    IWrappedShader fromFragment(FragmentShader fragmentShader);
}
