package gbw.melange.shading;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;

public class GLSL {

    public enum Type{

        INT("int"),
        BOOL("bool"),
        UINT("uint"),
        FLOAT("float"),
        DOUBLE("double");
        public final String glValue;
        Type(String glValue){
            this.glValue = glValue;
        }
        @Override
        public String toString(){
            return this.glValue;
        }
    }

    public static String toString(Color color){
        return vec4(color.r, color.g, color.b, color.a);
    }
    public static String toString(Vector4 vec4){
        return vec4(vec4.x, vec4.y, vec4.z, vec4.w);
    }
    public static String toString(Vector3 vec3){
        return vec3(vec3.x, vec3.y, vec3.z);
    }
    public static String toString(Vector2 vec2){
        return vec2(vec2.x, vec2.y);
    }
    public static String vec2(double x, double y){
        return "vec2(" + (float) x + ", " + (float) y + ")";
    }
    public static String vec3(double x, double y, double z){
        return "vec3(" + (float) x + ", " + (float) y + ", " + (float) z + ")";
    }
    public static String vec4(double x, double y, double z, double w){
        return "vec4(" + (float) x + ", " + (float) y + ", " + (float) z + ", " + (float) w + ")";
    }
    public static String array(Double... values){
        return array(Type.DOUBLE, values);
    }
    public static String array(Integer... values){
        return array(Type.INT, values);
    }
    public static String array(Float... values){
        return array(Type.FLOAT, values);
    }
    //JS moment
    public static String array(Type type, Number... values){
        StringBuilder sb = new StringBuilder();
        sb.append(type).append("[](");
        for(int i = 0; i < values.length; i++){
            sb.append(values[i]);
            if(i < values.length - 1){
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }

}
