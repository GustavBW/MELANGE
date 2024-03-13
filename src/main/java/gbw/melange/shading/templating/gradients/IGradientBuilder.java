package gbw.melange.shading.templating.gradients;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.shading.IWrappedShader;

/**
 * <p>IGradientBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IGradientBuilder {
    /**
     * <p>setInterpolationType.</p>
     *
     * @param type a {@link gbw.melange.shading.templating.gradients.InterpolationType} object
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder setInterpolationType(InterpolationType type);

    /**
     * <p>build.</p>
     *
     * @return a {@link gbw.melange.shading.IWrappedShader} object
     */
    IWrappedShader build();

    /**
     * Set the rotation of the gradient. The pivot point is in the middle of the element's mesh.
     *
     * @param degrees a double
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder setRotation(double degrees);

    /**
     * Add a stop to the gradient. If
     *
     * @param color the color to reach at this point
     * @param relativePosition a relative position as a value between 0 and 1. 0 is the start, 1 is the end.
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder addStops(Color color, double relativePosition);

    /**
     * See {@link gbw.melange.shading.templating.gradients.IGradientBuilder#addStops(Color, double)}
     *
     * @param c0 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos0 a double
     * @param c1 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos1 a double
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1);
    /**
     * See {@link gbw.melange.shading.templating.gradients.IGradientBuilder#addStops(Color, double)}
     *
     * @param c0 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos0 a double
     * @param c1 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos1 a double
     * @param c2 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos2 a double
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2);
    /**
     * See {@link gbw.melange.shading.templating.gradients.IGradientBuilder#addStops(Color, double)}
     *
     * @param c0 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos0 a double
     * @param c1 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos1 a double
     * @param c2 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos2 a double
     * @param c3 a {@link com.badlogic.gdx.graphics.Color} object
     * @param pos3 a double
     * @return a {@link gbw.melange.shading.templating.gradients.IGradientBuilder} object
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2, Color c3, double pos3);

}
