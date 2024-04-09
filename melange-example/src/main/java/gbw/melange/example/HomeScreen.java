package gbw.melange.example;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.mesh.services.Shapes;
import gbw.melange.common.shading.generative.noise.IPerlinNoiseShader;
import gbw.melange.common.shading.services.Colors;
import gbw.melange.common.shading.services.IShaderPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
@View(layer = View.HOME_SCREEN_LAYER, focusPolicy = View.FocusPolicy.RETAIN_LATEST)
public class HomeScreen implements OnRender {
    private static final Logger log = LogManager.getLogger();

    private IPerlinNoiseShader perlinA;
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider, Colors colors, IShaderPipeline pipeline, Shapes shapes) {
        IScreenSpace space = provider.getScreenSpace(this);

        perlinA = colors.perlin()
            .setFrequency(10)
            .setOctaves(8)
            .setPersistence(.1)
            .build();

        space.createElement()
            .styling()
            .setBackgroundColor(colors.constant(Color.CORAL))
            .setBorderColor(colors.constant(Color.FIREBRICK))
            .setBorderRadius(.1)
            .apply()
            .constraints()
            .setBorderWidth(5)
            .apply()
            .build();

        space.createElement()
            .styling()
            .setBackgroundColor(perlinA)
            .setBorderColor(colors.constant(Color.CYAN))
            .apply()
            .constraints()
            .setBorderWidth(5)
            .apply()
            .build();

    }

    private double acc;
    @Override
    public void onRender(double deltaT) {
        acc += deltaT;

    }
}