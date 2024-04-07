package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import gbw.melange.common.mesh.constants.IVertAttr;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec4;
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

    public static final IVertAttr<ISliceVec3> POSITION = new VertAttr<>( 3, "a_position", 1, ISliceVec3.class, SliceProviders.createVec3);
    public static final IVertAttr<ISliceVec4> COLOR_UNPACKED = new VertAttr<>( 4, "a_color", 2, ISliceVec4.class, SliceProviders.createVec4);
    public static final IVertAttr<ISliceVec4> COLOR_PACKED = new VertAttr<>( 4, "a_color", 4, ISliceVec4.class, SliceProviders.createVec4);
    public static final IVertAttr<ISliceVec3> NORMAL = new VertAttr<>( 3, "a_normal", 8, ISliceVec3.class, SliceProviders.createVec3);
    public static final IVertAttr<ISliceVec2> UV = new VertAttr<>(2, "a_texCoord0", 16, ISliceVec2.class, SliceProviders.createVec2); // Primary UV mapping
    //32 doesn't exist??
    public static final IVertAttr<ISliceVec2> BONE_WEIGHT = new VertAttr<>(2, "a_boneWeight", 64, ISliceVec2.class, SliceProviders.createVec2);
    public static final IVertAttr<ISliceVec3> TANGENT = new VertAttr<>(3, "a_tangent", 128, ISliceVec3.class, SliceProviders.createVec3);
    public static final IVertAttr<ISliceVec3> BI_NORMAL = new VertAttr<>( 3, "a_binormal", 256, ISliceVec3.class, SliceProviders.createVec3);





    private static IVertAttr<?>[] values = null;
    public static IVertAttr<?>[] values() {
        if(values != null && values.length != 0) {
            return values;
        }
        List<IVertAttr<?>> attrs = new ArrayList<>();
        Field[] fields = KnownAttributes.class.getDeclaredFields();

        for (Field field : fields) {
            boolean isCandidate = Modifier.isStatic(field.getModifiers())
                    && Modifier.isFinal(field.getModifiers())
                    && field.getType().equals(IVertAttr.class);
            if (isCandidate) {
                try {
                    attrs.add((IVertAttr<?>) field.get(null)); // Access the static field value
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        values = attrs.toArray(new IVertAttr[0]);
        return values;
    }

    private static final Logger log = LogManager.getLogger();


    public static List<IVertAttr<?>> convert(VertexAttributes attrs){
        List<IVertAttr<?>> attributes = new ArrayList<>(attrs.size());
        for (VertexAttribute libgdxAttr : attrs) {
            boolean found = false;
            for (IVertAttr<?> eAttr : KnownAttributes.values()) {
                if (eAttr.equals(libgdxAttr)) {
                    attributes.add(eAttr);
                    found = true; // Break inner loop once a match is found
                    break;
                }
            }
            if(!found){
                log.warn("Unable to map IVertAttr " + stringRepOf(libgdxAttr) + " to any known vertex attributes.");
            }
        }
        return attributes;
    }
    public static VertexAttribute[] convert(Set<IVertAttr<?>> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        int index = 0;
        for(IVertAttr<?> current : attrs){
            VertexAttribute converted = new VertexAttribute(current.usage(), current.compCount(), current.alias());
            out[index] = converted;
            index++;
        }
        return out;
    }

    public static VertexAttribute[] convert(List<IVertAttr<?>> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        for(int i = 0; i < out.length; i++){
            IVertAttr<?> current = attrs.get(i);
            VertexAttribute converted = new VertexAttribute(current.usage(), current.compCount(), current.alias());
            out[i] = converted;
        }
        return out;
    }

    public static String stringRepOf(VertexAttribute va){
        return "IVertAttr{alias: "+ va.alias + ", usage: " + va.usage + ", compNum: " + va.numComponents + ", type: " + va.type + "}";
    }



    public static boolean compare(VertexAttribute vAttr, IVertAttr<?> evAttr){
        return evAttr.equals(vAttr);
    }

}
