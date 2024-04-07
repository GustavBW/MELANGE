package gbw.melange.common.shading.generative.voronoi;

import com.badlogic.gdx.math.Vector2;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.generative.IGenerativeShader;

import java.util.List;

public interface IVoronoiShader extends IGenerativeShader<IVoronoiShader> {
    float[] getPoints();
    void setPoints(List<Vector2> points);

}
