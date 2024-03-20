package gbw.melange.shading.shaders.gradients;

public enum GradientShaderAttr {
    ROTATION("u_rotation");

    public final String glValue;
    GradientShaderAttr(String glValue){
        this.glValue = glValue;
    }
}
