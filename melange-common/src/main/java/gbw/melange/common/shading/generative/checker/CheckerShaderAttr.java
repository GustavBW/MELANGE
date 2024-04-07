package gbw.melange.common.shading.generative.checker;

import gbw.melange.shading.generative.gradients.GradientShaderAttr;
/**
 * For all shader attributes used for templating, their toString is overridden to return the glsl value instead for ease of use.
 */
public enum CheckerShaderAttr {

    ROWS("u_rows"),
    COLUMNS("u_columns");

    public final String glValue;

    CheckerShaderAttr(String glValue){
        this.glValue = glValue;
    }

    @Override
    public String toString(){
        return this.glValue;
    }

}
