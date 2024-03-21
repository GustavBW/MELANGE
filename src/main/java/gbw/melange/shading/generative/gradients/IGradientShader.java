package gbw.melange.shading.generative.gradients;

import gbw.melange.shading.generative.IWrappedShader;

public interface IGradientShader extends IWrappedShader<IGradientShader> {
    void setRotation(double deg);
}
