package gbw.melange.shading.generative.gradients;

import gbw.melange.shading.IWrappedShader;

public interface IGradientShader extends IWrappedShader<IGradientShader> {
    void setRotation(double deg);
}
