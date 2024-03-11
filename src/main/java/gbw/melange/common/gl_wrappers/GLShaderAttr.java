package gbw.melange.common.gl_wrappers;

public enum GLShaderAttr {
    POSITION("a_position"),
    MATRIX("u_projTrans"),
    COLOR("a_color"),
    TEXTURE("u_texture"),
    VERTEX_COORDS("v_texCoords"),
    NORMAL("a_normal"),
    TANGENT("a_tangent"),
    TEX_COORD0("a_texCoord0"),
    BONE_WEIGHTS("a_boneWeights"),
    BONE_INDICES("a_boneIndices");

    private final String name;

    GLShaderAttr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
