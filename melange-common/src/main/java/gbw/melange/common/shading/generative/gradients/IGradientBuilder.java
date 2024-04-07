package gbw.melange.common.shading.generative.gradients;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.shading.constants.InterpolationType;

/**
 * <p>IGradientBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IGradientBuilder {

    IGradientBuilder setInterpolationType(InterpolationType type);
    IGradientShader build();

    /**
     * Add a stop to the gradient. If
     *
     * @param color the color to reach at this point
     * @param relativePosition a relative position as a value between 0 and 1. 0 is the start, 1 is the end.
     */
    IGradientBuilder addStops(Color color, double relativePosition);
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1);
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2);
    IGradientBuilder addStops(Color c0, double pos0, Color c1, double pos1,  Color c2, double pos2, Color c3, double pos3);

}
