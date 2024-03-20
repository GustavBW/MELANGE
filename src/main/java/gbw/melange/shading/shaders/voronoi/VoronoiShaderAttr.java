package gbw.melange.shading.shaders.voronoi;

public enum VoronoiShaderAttr {
    POINTS("u_points");

    public final String glValue;
    VoronoiShaderAttr(String glValue){
        this.glValue = glValue;
    }
}
