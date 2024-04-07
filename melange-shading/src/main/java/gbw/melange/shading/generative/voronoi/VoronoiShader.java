package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import gbw.melange.common.shading.generative.voronoi.IVoronoiShader;
import gbw.melange.common.shading.generative.voronoi.VoronoiShaderAttr;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.VecUtil;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.GenerativeShader;
import gbw.melange.shading.components.IFragmentShader;
import gbw.melange.shading.components.VertexShader;

import java.util.List;

/**
 * Decorator for {@link ManagedShader} adding specific bindings and methods.
 */
public class VoronoiShader extends GenerativeShader<IVoronoiShader> implements IVoronoiShader{

    private float[] points = new float[0];
    private boolean hasChanged = true;
    public VoronoiShader(String localName, VertexShader vertex, IFragmentShader fragment){
        this(localName, vertex, fragment, true);
    }
    public VoronoiShader(String localName, VertexShader vertex, IFragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    @Override
    public void setPoints(List<Vector2> points) {
        hasChanged = true;
        this.points = VecUtil.flattenVec2(points);
    }
    @Override
    public float[] getPoints() {
        hasChanged = true;
        return points;
    }
    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }
    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniform2fv(VoronoiShaderAttr.POINTS.glValue, points, 0, points.length);
        program.setUniformi("u_num"+VoronoiShaderAttr.POINTS.glValue, points.length);
        hasChanged = false;
    }

    @Override
    protected boolean hasChildPropertiesChanged() {
        return hasChanged;
    }

    @Override
    protected void disposeChildSpecificResources() {

    }

    @Override
    protected IVoronoiShader copyChildAs(String newLocalName) {
        return new VoronoiShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected IVoronoiShader copyChild() {
        return new VoronoiShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

}
