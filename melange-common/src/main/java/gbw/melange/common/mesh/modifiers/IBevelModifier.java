package gbw.melange.common.mesh.modifiers;

/**
 * <p>IBevelModifier interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IBevelModifier extends MeshModifier {
    /**
     * @param num How many vertices to insert. More will mean a smoother rounded edge,
     * less will mean more performant.
     */
    void setResolution(int num);
    /**
     * @param angleDeg What corners of the mesh should be beveled. E.g. an angle threshold of 30 deg,
     * will make beveling only occur for corners sharper than 30 degrees.
     */
    void setAngleThreshold(double angleDeg);
    /**
     * @param absoluteRelativeDistance How "wide" should the bevel be in terms of relative screen space.
     * "Absolute" is used here to indicate that, in the space of the mesh, the distance is absolute as bevel distance,
     * at least in Blender, is usually based on actual side lengths (relative to mesh)
     */
    void setWidth(double absoluteRelativeDistance);

}
