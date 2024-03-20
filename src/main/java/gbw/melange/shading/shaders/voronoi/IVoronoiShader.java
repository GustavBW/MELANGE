package gbw.melange.shading.shaders.voronoi;

import com.badlogic.gdx.math.Vector2;
import gbw.melange.shading.shaders.IWrappedShader;

import java.util.List;

public interface IVoronoiShader extends IWrappedShader<IVoronoiShader> {
    float[] getPoints();

    void setPoints(List<Vector2> points);

}
