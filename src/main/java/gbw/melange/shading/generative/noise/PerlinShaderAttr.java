package gbw.melange.shading.generative.noise;

public enum PerlinShaderAttr {
    FREQUENCY("u_frequency"),
    OCTAVES("u_octaves"),
    PERSISTENCE("u_persistence");
    public final String glValue;
    PerlinShaderAttr(String glValue){
        this.glValue = glValue;
    }
    @Override
    public String toString(){
        return this.glValue;
    }
}
