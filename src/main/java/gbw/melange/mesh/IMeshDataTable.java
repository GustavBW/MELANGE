package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import gbw.melange.mesh.constants.EVertexAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMeshDataTable {
    /**
     * Extract the triangles as {@link Face} based on the indicies of the original mesh
     *
     * @param allFacesOfVert fill this provided map to create a lookup table between a given vertex position (vector 3, x, y, z) and all faces this vertex is a part of
     */
    List<Face> calculateFaces(Map<Vector3, List<Face>> allFacesOfVert);

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
    List<Vector3> extractVector3(EVertexAttribute key, int expectedOutputLength);

    List<Vector2> extractVector2(EVertexAttribute key, int expectedOutputLength);

    List<Vector4> extractVector4(EVertexAttribute key, int expectedOutputLength);

    Mesh convertToMesh();

    /**
     * @return true if the extraction can progress without issue
     */
    boolean checkExtraction(EVertexAttribute key, int expectedOutputLength, String transformingTo);

    IMeshDataTable copy();
}
