package gbw.melange.shading.postprocessing;

import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.generative.gradients.IGradientShader;

public interface IBoxBlurShader extends IPostProcessShader {

    void setKernelSize(int value);
}
