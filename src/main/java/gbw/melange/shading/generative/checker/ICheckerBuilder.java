package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.Color;

public interface ICheckerBuilder {

    ICheckerShader build();
    /**
     * Set the rotation of the checker pattern. The pivot point is in the middle of the element's mesh.
     */
    ICheckerBuilder setRotation(double degrees);
    ICheckerBuilder setFirstColor(Color color);
    ICheckerBuilder setSecondColor(Color color);
    ICheckerBuilder setRows(double count);
    ICheckerBuilder setColumns(double count);

}
