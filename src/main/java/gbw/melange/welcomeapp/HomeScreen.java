package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.MeshTable;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.shading.templating.gradients.InterpolationType;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@View( layer = View.HOME_SCREEN )
public class HomeScreen implements IHomeScreen {
    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider) {
        FragmentShader fragmentShader = GradientFragmentShaderBuilder.create(11.75)
                .addStop(Color.WHITE, 0)
                .addStop(Color.ROYAL, .5)
                .addStop(Color.CORAL, 1)
                .build();

        log.info("Got it: " + provider);
        System.out.println(fragmentShader.code());
        provider.getScreenSpace(this)
                .createElement()
                .setMesh(MeshTable.SQUARE.getMesh())
                .setBackgroundColor(fragmentShader)
                .setBorderColor(FragmentShader.constant(Color.WHITE))
                .setBorderWidth(1)
                .build();
    }
}
