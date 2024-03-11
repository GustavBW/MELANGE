package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.operations.IReallocatingOperation;

public interface IBevelOperation extends IReallocatingOperation<Mesh, Mesh> {
    /**
     * @param num How many vertices to insert
     */
    void setVertices(int num);
    /**
     * @param angleDeg What corners of the mesh should be beveled. E.g. an angle threshold of 30 deg, will make beveling only occur for corners sharper than 30 degrees.
     */
    void setAngleThreshold(double angleDeg);
    /**
     * @param absoluteRelativeDistance How "wide" should the bevel be in terms of relative screen space. "Absolute" is used here to indicate that, in the space of the mesh, the distance is absolute as bevel distance, at least in Blender, is usually based on actual side lengths (relative to mesh)
     */
    void setWidth(double absoluteRelativeDistance);

}