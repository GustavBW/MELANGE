package gbw.melange.mesh.formatting;


import gbw.melange.mesh.formatting.slicing.ISliceVec3;

/**
 * @author GustavBW
 */
public record Face(ISliceVec3 v0, ISliceVec3 v1, ISliceVec3 v2) {
}
