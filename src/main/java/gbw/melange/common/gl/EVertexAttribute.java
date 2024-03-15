package gbw.melange.common.gl;

import com.badlogic.gdx.graphics.VertexAttribute;

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


    /**
     * <p>componentCount.</p>
     *
     * @return a int
     */
    public int componentCount() {
        return numComponents;
    }

    /**
     * <p>alias.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String alias() {
        return alias;
    }

    /**
     * <p>usage.</p>
     *
     * @return a int
     */
    public int usage(){
        return usage;
    }

    /**
     * <p>equals.</p>
     *
     * @param vAttr a {@link com.badlogic.gdx.graphics.VertexAttribute} object
     * @return a boolean
     */
    public boolean equals(VertexAttribute vAttr){
        return vAttr.usage == this.usage()
                && vAttr.numComponents == this.componentCount()
                && vAttr.alias.equals(this.alias());
    }
    /**
     * <p>asVA.</p>
     *
     * @return a {@link com.badlogic.gdx.graphics.VertexAttribute} object
     */
    public VertexAttribute asVA(){
        return new VertexAttribute(this.usage, this.numComponents, this.alias);
    }

    /**
     * <p>compare.</p>
     *
     * @param vAttr a {@link com.badlogic.gdx.graphics.VertexAttribute} object
     * @param evAttr a {@link gbw.melange.common.gl.EVertexAttribute} object
     * @return a boolean
     */
    public static boolean compare(VertexAttribute vAttr, EVertexAttribute evAttr){
        return evAttr.equals(vAttr);
    }

}
