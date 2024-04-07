package gbw.melange.common.shading.generative.gradients;
/**
 * For all shader attributes used for templating, their toString is overridden to return the glsl value instead for ease of use.
 */
public enum GradientShaderAttr {
    NONE("u_none");

    public final String glValue;
    GradientShaderAttr(String glValue){
        this.glValue = glValue;
    }

    @Override
    public String toString() {
        return this.glValue;
    }
}
