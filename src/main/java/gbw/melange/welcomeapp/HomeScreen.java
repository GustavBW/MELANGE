package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.mesh.services.Shapes;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.constants.InterpolationType;
import gbw.melange.shading.constants.Vec2DistFunc;
import gbw.melange.shading.generative.checker.ICheckerShader;
import gbw.melange.shading.generative.noise.IPerlinNoiseShader;
import gbw.melange.shading.generative.voronoi.VoronoiFragmentBuilder;
import gbw.melange.shading.postprocessing.BoxBlurShader;
import gbw.melange.shading.services.Colors;
import gbw.melange.shading.services.IShaderPipeline;
import gbw.melange.shading.generative.voronoi.IVoronoiShader;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * <p>HomeScreen class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@View(layer = View.HOME_SCREEN_LAYER, focusPolicy = View.FocusPolicy.RETAIN_LATEST)
public class HomeScreen implements IHomeScreen, OnRender {
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
