package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.MeshTable;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.elements.ComputedTransforms;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@View( layer = View.HOME_SCREEN )
public class HomeScreen implements IHomeScreen {
    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider) {
        FragmentShader fragmentShader = new GradientFragmentShaderBuilder()
                .addStops(Color.MAGENTA, 0,Color.ROYAL, .5, Color.CYAN, 1)
                .setRotation(45)
                .build();

        IScreenSpace space = provider.getScreenSpace(this);

        IElement element = space.createElement()
                .setMesh(MeshTable.CIRCLE_64.getMesh()) //TODO: Introduce rotation. Only thing users are allowed to set
                .styling()
                    .setBackgroundColor(fragmentShader)
                    .setBorderColor(Color.WHITE)
                    .setBackgroundDrawStyle(GLDrawStyle.TRIANGLE_STRIP)
                    .apply()
                .constraints()
                    .setBorderWidth(2)
                    .apply()
                .build();

        //TODO: Hella illegal moves
        ((ComputedTransforms) element.computed()).getMatrix().setToScaling(.9f, .9f, .9f);
    }
}
