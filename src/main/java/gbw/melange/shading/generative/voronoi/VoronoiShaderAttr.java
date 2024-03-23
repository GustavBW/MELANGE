package gbw.melange.shading.generative.voronoi;

/**
 * For all shader attributes used for templating, their toString is overridden to return the glsl value instead for ease of use.
 */
public enum VoronoiShaderAttr {
    POINTS("u_points");

    public final String glValue;
    VoronoiShaderAttr(String glValue){
        this.glValue = glValue;
    }

    @Override
    public String toString() {
        return this.glValue;
    }
}
