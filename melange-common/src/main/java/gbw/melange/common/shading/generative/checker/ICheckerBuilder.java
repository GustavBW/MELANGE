package gbw.melange.common.shading.generative.checker;

import com.badlogic.gdx.graphics.Color;

public interface ICheckerBuilder {

    ICheckerShader build();
    ICheckerBuilder setFirstColor(Color color);
    ICheckerBuilder setSecondColor(Color color);
    ICheckerBuilder setRows(double count);
    ICheckerBuilder setColumns(double count);

}
