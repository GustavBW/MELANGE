package gbw.melange.welcomeapp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import gbw.melange.common.MeshTable;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.common.elementary.space.IScreenSpace;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.elements.ComputedTransforms;
import gbw.melange.elements.Element;
import gbw.melange.shading.FragmentShader;
import gbw.melange.shading.ShaderProgramWrapper;
import gbw.melange.shading.VertexShader;
import gbw.melange.shading.postprocessing.PostProcessShader;
import gbw.melange.shading.templating.gradients.GradientFragmentShaderBuilder;
import gbw.melange.welcomeapp.processors.IHomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@View( layer = View.HOME_SCREEN )
public class HomeScreen implements IHomeScreen, OnRender {
    private static final Logger log = LoggerFactory.getLogger(HomeScreen.class);
    private IElement element;
    @Autowired
    public HomeScreen(ISpaceProvider<IScreenSpace> provider) {
        FragmentShader fragmentShader = new GradientFragmentShaderBuilder("HomeScreenGradient")
            .addStops(Color.MAGENTA, 0,Color.ROYAL, .5, Color.CYAN, 1)
            .setRotation(45)
            .build();

        IScreenSpace space = provider.getScreenSpace(this);

        space.createElement().setMesh(MeshTable.RHOMBUS.getMesh())
                .styling()
                //.setBackgroundColor(FragmentShader.DEBUG_UV)
                .apply()
                .build();

        element = space.createElement()
            .setMesh(MeshTable.CIRCLE_64.getMesh()) //TODO: Introduce rotation. Only thing users are allowed to set
            .styling()
                .setBackgroundColor(FragmentShader.DEBUG_UV)
                .setBorderColor(Color.WHITE)
                //.addPostProcess(new PostProcessShader(new ShaderProgram(FragmentShader.DEBUG_UV.code(), VertexShader.DEFAULT.code())))
                .apply()
            .build();

        space.createSpace().build();

        ((ComputedTransforms) element.computed()).setScale(.9f, .9f, .9f);
    }
    private double acc = 0;
    @Override
    public void onRender(double deltaT) {
        acc += deltaT;
        ((ComputedTransforms) element.computed())
                .setTranslation(MathUtils.sin((float) acc), MathUtils.cos((float) acc), 0);
    }
}
