package gbw.melange.shading.generative.gradients;
/**
 * For all shader attributes used for templating, their toString is overridden to return the glsl value instead for ease of use.
 */
public enum GradientShaderAttr {
    ROTATION("u_rotation");

    public final String glValue;
    GradientShaderAttr(String glValue){
        this.glValue = glValue;
    }

    @Override
    public String toString() {
        return this.glValue;
    }
}
