package gbw.melange.shading.generative.checker;

import gbw.melange.shading.generative.gradients.GradientShaderAttr;

public enum CheckerShaderAttr {

    ROWS("u_rows"),
    COLUMNS("u_columns"),
    ROTATION(GradientShaderAttr.ROTATION.glValue);

    public final String glValue;

    CheckerShaderAttr(String glValue){
        this.glValue = glValue;
    }

}
