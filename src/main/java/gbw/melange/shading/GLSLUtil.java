package gbw.melange.shading;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector4;

public class GLSLUtil {

    public static String toString(Color color){
        return vec4(color.r, color.g, color.b, color.a);
    }
    public static String toString(Vector4 vec4){
        return vec4(vec4.x, vec4.y, vec4.z, vec4.w);
    }
    public static String vec2(double x, double y){
        return "vec2(" + (float) x + ", " + (float) y + ")";
    }
    public static String vec3(double x, double y, double z){
        return "vec2(" + (float) x + ", " + (float) y + ", " + (float) z + ")";
    }
    public static String vec4(double x, double y, double z, double w){
        return "vec2(" + (float) x + ", " + (float) y + ", " + (float) z + ", " + (float) w + ")";
    }


}
