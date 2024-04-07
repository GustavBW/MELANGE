package gbw.melange.common.shading.postprocess;

public enum BlurShaderAttr {
    KERNEL_SIZE("kernelSize");

    public final String glValue;
    BlurShaderAttr(String glValue){
        this.glValue = glValue;
    }
}
