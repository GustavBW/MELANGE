package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.math.Vector2;
import gbw.melange.shading.IWrappedShader;

import java.util.List;

public interface IVoronoiShader extends IWrappedShader<IVoronoiShader> {
    float[] getPoints();

    void setPoints(List<Vector2> points);

}
