package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.MeshTable;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.common.navigation.ISpaceNavigator;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@View(layer = View.HOME_SCREEN_LAYER, focusPolicy = View.FocusPolicy.RETAIN_LATEST)
public class HomeScreen implements IHomeScreen, OnRender {
    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider, ISpaceNavigator navigator) {
        IScreenSpace space = provider.getScreenSpace(this);
        FragmentShader fragmentShader = new GradientFragmentShaderBuilder("HomeScreenGradient")
                .addStops(Color.MAGENTA, 0,Color.ROYAL, .5, Color.CYAN, 1)
                .setRotation(45)
                .build();
        final double borderWidth = 5;

        space.createElement().setMesh(MeshTable.SQUARE.getMesh())
            .styling()
                .setBackgroundColor(FragmentShader.DEBUG_UV)
                .setBorderColor(Color.WHITE)
                .apply()
            .constraints()
                .setBorderWidth(borderWidth)
                .apply()
            .build();

        space.createElement()
            .setMesh(MeshTable.CIRCLE_64.getMesh()) //TODO: Introduce rotation. Only thing users are allowed to set
            .styling()
                .setBackgroundColor(FragmentShader.DEBUG_UV)
                .setBorderColor(Color.WHITE)
                .apply()
            .constraints()
                .setBorderWidth(borderWidth)
                .apply()
            .build();


        space.createElement().setMesh(MeshTable.RHOMBUS.getMesh())
                .styling()
                    .setBackgroundColor(FragmentShader.DEBUG_UV)
                    .setBorderColor(Color.WHITE)
                    .apply()
                .constraints()
                    .setBorderWidth(borderWidth)
                    .apply()
                .build();

        space.createElement().setMesh(MeshTable.EQUILATERAL_TRIANGLE.getMesh())
                .styling()
                    .setBackgroundColor(FragmentShader.DEBUG_UV)
                    .setBorderColor(Color.WHITE)
                    .apply()
                .constraints()
                    .setBorderWidth(borderWidth)
                    .apply()
                .build();
    }
    private double acc = 0;
    @Override
    public void onRender(double deltaT) {
    }
}
