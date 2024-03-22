package gbw.melange.shading.generative.voronoi;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.constants.InterpolationType;
import gbw.melange.shading.constants.Vec2DistFunc;
import gbw.melange.shading.generative.noise.NoiseProvider;
import gbw.melange.shading.generative.noise.PerlinNoise;
/**
 * <p>IVoronoiFragmentBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IVoronoiFragmentBuilder {

    int MAX_NUM_POINTS = 100;

    /**
     * Add a specific point up to a maximum of {@link IVoronoiFragmentBuilder#MAX_NUM_POINTS}.
     * If points are provided through this builder, the builder will mark the generated shader as static,
     * and if the pipeline is present, it will be cached automatically.
     * This is the default behaviour for performance reasons, and can be overwritten using {@link IWrappedShader#setStatic(boolean)}
     * immediately afterwards.
     */
    IVoronoiFragmentBuilder addPoint(double x, double y);

    /**
     * Extract vertex position information from mesh and use as points for voronoi. Ignores Z.
     * Declaring points up front incurs some assumptions if the pipeline is in play see {@link IVoronoiFragmentBuilder#addPoint(double, double)}
     */
    IVoronoiFragmentBuilder vertsAsPoints(Mesh mesh);

    /**
     * Add any amount of randomly distributed points. All points' parameters are in a 0-1 space.
     * The default noise implementation is {@link PerlinNoise}
     * Declaring points up front incurs some assumptions if the pipeline is in play see {@link IVoronoiFragmentBuilder#addPoint(double, double)}
     */
    IVoronoiFragmentBuilder addRandomPoints(int num);

    /**
     * Add any amount of randomly distributed points from some provider implementing {@link NoiseProvider}.
     * Declaring points up front incurs some assumptions if the pipeline is in play see {@link IVoronoiFragmentBuilder#addPoint(double, double)}
     * @param num amount of points
     * @param provider
     *    {@code () -> double }
     */
    IVoronoiFragmentBuilder addRandomPoints(int num, NoiseProvider provider);

    /**
     * Set the distance function this shader should use to determine the distance between the pixel in question and the sourrounding points.
     * @default {@link Vec2DistFunc#EUCLIDEAN_SQUARED}
     */
    IVoronoiFragmentBuilder setDistanceType(Vec2DistFunc type);

    /**
     * Set the interpolation type used to determine the resulting color based on the distance.
     * In this case {@link InterpolationType#NONE} is allowed if one does not wish to map the color into a 0..1 range.
     * @default {@link InterpolationType#HERMIT}
     */
    IVoronoiFragmentBuilder setInterpolationType(InterpolationType interpolationType);

    IVoronoiShader build();
}
