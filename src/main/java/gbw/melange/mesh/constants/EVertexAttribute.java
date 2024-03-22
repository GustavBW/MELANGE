package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>EVertexAttribute class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum EVertexAttribute {
    POSITION( 3, "a_position", 1),
    COLOR_UNPACKED( 4, "a_color", 2),
    COLOR_PACKED( 4, "a_color", 4),
    NORMAL( 3, "a_normal", 8),
    UV(2, "a_texCoord0", 16), // Primary UV mapping
    //32 doesn't exist??
    BONE_WEIGHT(2, "a_boneWeight", 64),
    TANGENT(3, "a_tangent", 128),
    BI_NORMAL( 3, "a_binormal", 256);

    private static final Logger log = LogManager.getLogger();
    private final int numComponents;
    private final String alias;
    private final int usage;

    EVertexAttribute(int numComponents, String alias, int usage) {
        this.numComponents = numComponents;
        this.alias = alias;
        this.usage = usage;
    }

    public static List<EVertexAttribute> convert(VertexAttributes attrs){
        List<EVertexAttribute> attributes = new ArrayList<>();
        for (VertexAttribute attr : attrs) {
            boolean found = false;
            for (EVertexAttribute eAttr : EVertexAttribute.values()) {
                if (eAttr.equals(attr)) {
                    attributes.add(eAttr);
                    found = true; // Break inner loop once a match is found
                    break;
                }
            }
            if(!found){
                log.warn("Unable to map VertexAttribute " + stringRepOf(attr) + " to any known EVertexAttribute.");
            }
        }
        return attributes;
    }
    public static VertexAttribute[] convert(Set<EVertexAttribute> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        int index = 0;
        for(EVertexAttribute current : attrs){
            VertexAttribute converted = new VertexAttribute(current.usage(), current.componentCount(), current.alias());
            out[index] = converted;
            index++;
        }
        return out;
    }

    public static VertexAttribute[] convert(List<EVertexAttribute> attrs){
        VertexAttribute[] out = new VertexAttribute[attrs.size()];
        for(int i = 0; i < out.length; i++){
            EVertexAttribute current = attrs.get(i);
            VertexAttribute converted = new VertexAttribute(current.usage(), current.componentCount(), current.alias());
            out[i] = converted;
        }
        return out;
    }

    public static String stringRepOf(VertexAttribute va){
        return "VertexAttribute{alias: "+ va.alias + ", usage: " + va.usage + ", compNum: " + va.numComponents + ", type: " + va.type + "}";
    }


    public int componentCount() {
        return numComponents;
    }


    public String alias() {
        return alias;
    }


    public int usage(){
        return usage;
    }


    public boolean equals(VertexAttribute vAttr){
        return vAttr.usage == this.usage()
                && vAttr.numComponents == this.componentCount()
                && vAttr.alias.equals(this.alias());
    }
    public VertexAttribute asVA(){
        return new VertexAttribute(this.usage, this.numComponents, this.alias);
    }
    public static boolean compare(VertexAttribute vAttr, EVertexAttribute evAttr){
        return evAttr.equals(vAttr);
    }

}
