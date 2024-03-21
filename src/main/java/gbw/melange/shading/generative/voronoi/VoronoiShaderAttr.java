package gbw.melange.shading.generative.voronoi;

public enum VoronoiShaderAttr {
    POINTS("u_points");

    public final String glValue;
    VoronoiShaderAttr(String glValue){
        this.glValue = glValue;
    }
}
