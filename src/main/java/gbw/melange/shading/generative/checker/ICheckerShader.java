package gbw.melange.shading.generative.checker;

import gbw.melange.shading.IManagedShader;

public interface ICheckerShader extends IManagedShader<ICheckerShader> {

    /**
     * Set the amount of columns shown. Partial (non-integer) values show the last row as being "cut-off" allowing for smooth animations.
     */
    void setColumns(double count);
    /**
     * Set the amount of rows shown. Partial (non-integer) values show the last row as being "cut-off" allowing for smooth animations.
     */
    void setRows(double count);
    void setRotation(double deg);

}
