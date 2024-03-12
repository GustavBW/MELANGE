package gbw.melange.mesh;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

import java.util.Comparator;

public enum EVertexAttribute {
    POSITION( 3, "a_position", 1),
    COLOR_UNPACKED( 4, "a_color", 2),
    COLOR_PACKED( 4, "a_color", 4),
    NORMAL( 3, "a_normal", 8),
    TEX_COORDS( 2, "a_texCoords", 16),
    //32 doesn't exist??
    BONE_WEIGHT(2, "a_boneWeight", 64),
    TANGENT(3, "a_tangent", 128),
    BI_NORMAL( 3, "a_binormal", 256);

    private final int numComponents;
    private final String alias;
    private final int usage;

    EVertexAttribute(int numComponents, String alias, int usage) {
        this.numComponents = numComponents;
        this.alias = alias;
        this.usage = usage;
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
