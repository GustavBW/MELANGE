package gbw.melange.shading.shaders.gradients;

import gbw.melange.shading.shaders.IWrappedShader;

public interface IGradientShader extends IWrappedShader<IGradientShader> {
    void setRotation(double deg);
}
