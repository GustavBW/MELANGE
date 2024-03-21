package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.VecUtil;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.WrappedShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for {@link WrappedShader} adding specific bindings and methods.
 */
public class VoronoiShader extends WrappedShader<IVoronoiShader> implements IVoronoiShader{

    private float[] points = new float[0];
    public VoronoiShader(String localName, VertexShader vertex, FragmentShader fragment){
        this(localName, vertex, fragment, true, new ArrayList<>());
    }
    public VoronoiShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    @Override
    public void setPoints(List<Vector2> points) {
        this.points = VecUtil.flattenVec2(points);
    }
    @Override
    public float[] getPoints() {
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
    }
    @Override
    protected void disposeChildSpecificResources() {

    }

    @Override
    protected IVoronoiShader copyChildAs(String newLocalName) {
        return new VoronoiShader(newLocalName, getVertex(), getFragment(), isStatic(), super.bindings);
    }

    @Override
    protected IVoronoiShader copyChild() {
        return new VoronoiShader(super.shortName(), getVertex(), getFragment(), isStatic(), super.bindings);
    }

}
