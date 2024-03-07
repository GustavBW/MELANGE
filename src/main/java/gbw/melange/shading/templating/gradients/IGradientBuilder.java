package gbw.melange.shading.templating.gradients;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.builders.IBuilder;
import gbw.melange.shading.FragmentShader;

public interface IGradientBuilder extends IBuilder<FragmentShader> {
    IGradientBuilder setInterpolationType(InterpolationType type);

    /**
     * Set the rotation of the gradient. The pivot point is in the middle of the element's mesh.
     */
    IGradientBuilder setRotation(double degrees);

    /**
     * Add a stop to the gradient. If
     * @param color the color to reach at this point
     * @param relativePosition a relative position as a value between 0 and 1. 0 is the start, 1 is the end.
     */
    IGradientBuilder addStops(Color color, double relativePosition);

    /**
     * See {@link IGradientBuilder#addStops(Color, double)}
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1);
    /**
     * See {@link IGradientBuilder#addStops(Color, double)}
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2);
    /**
     * See {@link IGradientBuilder#addStops(Color, double)}
     */
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2, Color c3, double pos3);

}
