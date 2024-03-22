package gbw.melange.mesh.operations;

/**
 * @param subdivs How many vertices to insert
 * @param angleThreshold What corners of the mesh should be beveled. E.g. an angle threshold of 30 deg, will make beveling only occur for corners sharper than 30 degrees.
 * @param absoluteRelativeDistance How "wide" should the bevel be in terms of relative screen space. "Absolute" used here to indicate that, in the space of the mesh, the distance is absolute as bevel distance, at least in Blender, is based on side lengths (relative to mesh)
 */
public record BevelConfig(int subdivs, double angleThreshold, double absoluteRelativeDistance) {
    public static final BevelConfig NONE = new BevelConfig(0, 180, 0);
    public static final BevelConfig DEFAULT = new BevelConfig(5, 45, .01);
}
