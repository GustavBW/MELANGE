package gbw.melange.shading.procedural.voronoi;

public enum VoronoiShaderAttr {
    POINTS("u_points");

    public final String glValue;
    VoronoiShaderAttr(String glValue){
        this.glValue = glValue;
    }
}
