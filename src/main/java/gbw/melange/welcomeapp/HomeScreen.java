package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.MeshTable;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.navigation.ISpaceNavigator;
import gbw.melange.shading.Colors;
import gbw.melange.shading.impl.FragmentShader;
import gbw.melange.shading.IShaderPipeline;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.procedural.gradients.GradientFragmentShaderBuilder;
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
public class HomeScreen implements IHomeScreen {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider, ISpaceNavigator navigator, Colors colors, IShaderPipeline pipeline) {
        IScreenSpace space = provider.getScreenSpace(this);
        IWrappedShader fragmentShader = new GradientFragmentShaderBuilder("HomeScreenGradient", pipeline)
                .addStops(Color.MAGENTA, 0,Color.ROYAL, .5, Color.CYAN, 1)
                .setRotation(45)
                .build();
        final double borderWidth = 5;

        space.createElement("HI")
            .styling()
                .setBackgroundColor(colors.fromFragment(FragmentShader.DEBUG_UV))
                .setBorderColor(colors.constant(Color.WHITE))
                .apply()
            .constraints()
                .setBorderWidth(borderWidth)
                .apply()
            .build();

        space.createElement(() -> "HI")
            .setMesh(MeshTable.CIRCLE_64.getMesh()) //TODO: Introduce rotation. Only thing users are allowed to set
            .styling()
                .setBackgroundColor(fragmentShader)
                .setBorderColor(colors.constant(Color.WHITE))
                .apply()
            .constraints()
                .setBorderWidth(borderWidth)
                .apply()
            .build();


        space.createElement().setMesh(MeshTable.RHOMBUS.getMesh())
                .styling()
                    .setBackgroundColor(colors.fromFragment(FragmentShader.DEBUG_UV))
                    .setBorderColor(colors.constant(Color.WHITE))
                    .apply()
                .constraints()
                    .setBorderWidth(borderWidth)
                    .apply()
                .build();

        space.createElement().setMesh(MeshTable.EQUILATERAL_TRIANGLE.getMesh())
                .styling()
                    .setBackgroundColor(fragmentShader)
                    .setBorderColor(colors.constant(Color.WHITE))
                    .apply()
                .constraints()
                    .setBorderWidth(borderWidth)
                    .apply()
                .build();
    }
}
