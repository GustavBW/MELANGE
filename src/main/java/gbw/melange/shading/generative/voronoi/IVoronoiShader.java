package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.math.Vector2;
import gbw.melange.shading.IManagedShader;

import java.util.List;

public interface IVoronoiShader extends IManagedShader<IVoronoiShader> {
    float[] getPoints();

    void setPoints(List<Vector2> points);

}
