package gbw.melange.shading;

public enum Vec2DistFunc {

    MANHATTAN("abs(pointA.x - pointB.x) + abs(pointA.y - pointB.y);"),
    EUCLIDEAN("sqrt(pow(pointA.x - pointB.x, 2.0) + pow(pointA.y - pointB.y, 2.0));"),
    /**
     * More performant as it avoids ever have to use sqrt
     */
    EUCLIDEAN_SQUARED("pow(pointA.x - pointB.x, 2.0) + pow(pointA.y - pointB.y, 2.0);"),
    CHEBYSHEV("max(abs(pointA.x - pointB.x), abs(pointA.y - pointB.y));");

    public final String glslCode;
    Vec2DistFunc(String glslCode){
        this.glslCode = glslCode;
    }
}
