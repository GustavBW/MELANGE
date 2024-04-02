package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.constants.EVertexAttribute;

import java.util.List;
import java.util.Map;

/**
 * An IMeshDataTable is a System memory representation of the vertex data for a given Mesh and is for utility
 * purposes only. It, in itself, assures 100% data retention on any operation, however, it is not hotpath safe.
 * Although somewhat optimized, the risk of varying time per operation per mesh vertex is too great.
 */
public interface IMeshDataTable {
    /**
     * Extract the triangles as {@link Face} based on the indicies of the original mesh
     *
     * @param allFacesOfVert fill this provided map to create a lookup table between a given vertex position (vector 3, x, y, z) and all faces this vertex is a part of
     */
    List<Face> calculateFaces(Map<IRefAccVec3, List<Face>> allFacesOfVert);

    /**
     * See {@link IMeshDataTable#calculateFaces(Map)} but without the generated lookup table
     */
    List<Face> calculateFaces();

    /**
     * Extract a row as a list of 3-dimensional vectors.
     *
     * @param key                  To extract as Vector3's
     * @param expectedOutputLength -1 to ignore, or actual value for additional error checking.
     */
    List<IRefAccVec3> extractVector3(EVertexAttribute key, int expectedOutputLength);

    List<IRefAccVec2> extractVector2(EVertexAttribute key, int expectedOutputLength);

    List<IRefAccVec4> extractVector4(EVertexAttribute key, int expectedOutputLength);

    Mesh convertToMesh();

    /**
     * @return true if the extraction can progress without issue
     */
    boolean checkExtraction(EVertexAttribute key, int expectedOutputLength, String transformingTo);

    IMeshDataTable copy();
}
