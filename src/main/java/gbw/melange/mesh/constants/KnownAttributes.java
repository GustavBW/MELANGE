package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>KnownAttributes class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class KnownAttributes {

    public static final IVertAttr POSITION = new VertAttr( 3, "a_position", 1);
    public static final IVertAttr COLOR_UNPACKED = new VertAttr( 4, "a_color", 2);
    public static final IVertAttr COLOR_PACKED = new VertAttr( 4, "a_color", 4);
    public static final IVertAttr NORMAL = new VertAttr( 3, "a_normal", 8);
    public static final IVertAttr UV = new VertAttr(2, "a_texCoord0", 16); // Primary UV mapping
    //32 doesn't exist??
    public static final IVertAttr BONE_WEIGHT = new VertAttr(2, "a_boneWeight", 64);
    public static final IVertAttr TANGENT = new VertAttr(3, "a_tangent", 128);
    public static final IVertAttr BI_NORMAL = new VertAttr( 3, "a_binormal", 256);

    private static IVertAttr[] values = null;
    public static IVertAttr[] values() {
        if(values != null) {
            return values;
        }
        List<IVertAttr> attrs = new ArrayList<>();
        Field[] fields = KnownAttributes.class.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(VertAttr.class)) {
                try {
                    attrs.add((IVertAttr) field.get(null)); // Access the static field value
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        values = attrs.toArray(new IVertAttr[0]);
        return values;
    }

    private static final Logger log = LogManager.getLogger();


    public static List<IVertAttr> convert(VertexAttributes attrs){
        List<IVertAttr> attributes = new ArrayList<>();
        for (VertexAttribute attr : attrs) {
            boolean found = false;
            for (IVertAttr eAttr : KnownAttributes.values()) {
                if (eAttr.equals(attr)) {
                    attributes.add(eAttr);
                    found = true; // Break inner loop once a match is found
                    break;
                }
            }
            if(!found){
                log.warn("Unable to map IVertAttr " + stringRepOf(attr) + " to any known KnownAttributes.");
            }
        }
        return attributes;
    }
    public static VertexAttribute[] convert(Set<IVertAttr> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        int index = 0;
        for(IVertAttr current : attrs){
            VertexAttribute converted = new VertexAttribute(current.usage(), current.compCount(), current.alias());
            out[index] = converted;
            index++;
        }
        return out;
    }

    public static VertexAttribute[] convert(List<IVertAttr> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        for(int i = 0; i < out.length; i++){
            IVertAttr current = attrs.get(i);
            VertexAttribute converted = new VertexAttribute(current.usage(), current.compCount(), current.alias());
            out[i] = converted;
        }
        return out;
    }

    public static String stringRepOf(VertexAttribute va){
        return "IVertAttr{alias: "+ va.alias + ", usage: " + va.usage + ", compNum: " + va.numComponents + ", type: " + va.type + "}";
    }



    public static boolean compare(VertexAttribute vAttr, IVertAttr evAttr){
        return evAttr.equals(vAttr);
    }

}
