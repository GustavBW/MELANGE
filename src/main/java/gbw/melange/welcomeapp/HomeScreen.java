package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.mesh.services.Shapes;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.constants.InterpolationType;
import gbw.melange.shading.constants.Vec2DistFunc;
import gbw.melange.shading.generative.voronoi.VoronoiFragmentBuilder;
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
    private final IVoronoiShader voronoiA;
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider, Colors colors, IShaderPipeline pipeline, Shapes shapes) {
        IScreenSpace space = provider.getScreenSpace(this);

        voronoiA = new VoronoiFragmentBuilder("HS_Gradient_A", pipeline)
                .addRandomPoints(40)
                .setDistanceType(Vec2DistFunc.EUCLIDEAN)
                .setInterpolationType(InterpolationType.NONE)
                .build();

        voronoiA.setStatic(true);

        /*
        space.createElement("HI")
            .styling()
                .setBackgroundColor(colors.fromFragment(FragmentShader.DEBUG_UV))
                .setBorderColor(colors.constant(Color.WHITE))
                .apply()
            .constraints()
                .setBorderWidth(borderWidth)
                .apply()
            .build();
         */

        /*
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
        */

        space.createElement()
                .setShape(shapes.square())
                .styling()
                    .setBackgroundColor(voronoiA)
                    .setBorderColor(colors.constant(Color.WHITE))
                    .setBorderRadius(.1)
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
        boolean flipThat = false;

        for(int i = 0; i + 1 < voronoiA.getPoints().length; i += 2){
            float xOff = MathUtils.sin((float) acc + (i / 3f)) / 100;
            float yOff = MathUtils.cos((float) acc + (i / 7f)) / 100;

            if(flipThat){
                voronoiA.getPoints()[i] += xOff;
                voronoiA.getPoints()[i + 1] += yOff;
            }else{
                voronoiA.getPoints()[i] -= xOff;
                voronoiA.getPoints()[i + 1] -= yOff;
            }

            flipThat = !flipThat;
        }

        if(acc >= 10){
            ((ManagedShader<IVoronoiShader>) voronoiA).setCachedTexture(null);
        }
    }
}
