package gbw.melange.common.shading.generative.checker;

import gbw.melange.shading.IManagedShader;
import gbw.melange.common.shading.generative.IGenerativeShader;

public interface ICheckerShader extends IGenerativeShader<ICheckerShader> {

    /**
     * Set the amount of columns shown. Partial (non-integer) values show the last row as being "cut-off" allowing for smooth animations.
     */
    void setColumns(double count);
    /**
     * Set the amount of rows shown. Partial (non-integer) values show the last row as being "cut-off" allowing for smooth animations.
     */
    void setRows(double count);

}
