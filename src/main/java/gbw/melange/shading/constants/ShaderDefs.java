package gbw.melange.shading.constants;

public enum ShaderDefs {
    /**
     * Fragment shaders may as part of a chain, or not, sample from a previous fragment shader output.
     * If a previous output is to be taken into account, this definition is present.
     */
    HAS_INPUT_TEXTURE("HAS_INPUT_TEXTURE");

    public final String defName;
    ShaderDefs(String defName){
        this.defName = defName;
    }

}
