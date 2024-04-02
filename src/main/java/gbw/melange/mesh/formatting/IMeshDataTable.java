package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.shading.errors.Error;

import java.util.List;
import java.util.Map;

/**
 * An IMeshDataTable is a System memory representation of the vertex data for a given Mesh and is for utility
 * purposes only. It, in itself, assures 100% data retention on any operation, however, it is not hotpath safe.
 * Although somewhat optimized, the risk of varying time per operation per mesh vertex is too great.
 */
public interface IMeshDataTable {

    /**
     * Add the data from another {@link IMeshDataTable} onto this as an irreversible process.
     * Indices will not be modified, however the underlying float buffers will be extended.
     */
    void add(IMeshDataTable other);

    /**
     * Add a new attribute to the mesh. If this attribute already exists, replace the current data.
     * Addition or replacement will happen in the vertex order known to the IMeshDataTable.
     * <ul>
     *     <li>If an insufficient amount of values are provided, an error is returned.</li>
     *     <li>If too much data is provided, the remainder is ignored.</li>
     *     <li>If the startIndex is not 0, and no data is already available for the attribute, an error is returned.</li>
     * </ul>
     * For a less stringent method see {@link IMeshDataTable#addOrReplaceAttribute(EVertexAttribute, int, float[], float)}
     * @param attr What attribute to alter the data of, or set new data for
     * @param data The actual float values to insert.
     * @param startIndex Where to start IN TERMS OF VERTEX NUMBER which is dependent on the {@link EVertexAttribute#componentCount()}
     * @return an {@link Error} if any, or {@link Error#NONE}
     */
    Error addOrReplaceAttribute(EVertexAttribute attr, int startIndex, float[] data);

    /**
     * Add a new attribute to the mesh. If this attribute already exists, replace the current data.
     * Addition or replacement will happen in the vertex order known to the {@link IMeshDataTable}.
     * <ul>
     *     <li>If an insufficient amount of values is provided, the rest are set to the fill value.</li>
     *     <li>If too much data is provided, the remainder is ignored.</li>
     *     <li>If the startIndex is not 0, and no data is already available for the attribute,
     *     the prior indexes are filled with the fill value.</li>
     * </ul>
     * For a more stringent method see {@link IMeshDataTable#addOrReplaceAttribute(EVertexAttribute, int, float[])}.
     * @param attr What attribute to alter the data of, or set new data for
     * @param data The actual float values to insert.
     * @return an {@link Error} if any, or {@link Error#NONE}
     */
    Error addOrReplaceAttribute(EVertexAttribute attr, int startIndex, float[] data, float fillValue);

    /**
     * See {@link IMeshDataTable#calculateFaces(Map)} but without the generated lookup table
     */
    List<Face> calculateFaces();
    /**
     * Extract the triangles as {@link Face} based on the indicies of the original mesh
     *
     * @param allFacesOfVert fill this provided map to create a lookup table between a given vertex position (vector 3, x, y, z) and all faces this vertex is a part of
     */
    List<Face> calculateFaces(Map<IRefAccVec3, List<Face>> allFacesOfVert);

    /**
     * Same as {@link IMeshDataTable#extractVector3(EVertexAttribute, int)} but with 2 component vectors.
     */
    List<IRefAccVec2> extractVector2(EVertexAttribute key, int expectedOutputLength);
    /**
     * Extract all data for a given vertex attribute as 3 component vectors.
     *
     * @param key                  To extract as Vector3's
     * @param expectedOutputLength -1 to ignore, or actual value for additional error checking.
     */
    List<IRefAccVec3> extractVector3(EVertexAttribute key, int expectedOutputLength);

    /**
     * Same as {@link IMeshDataTable#extractVector3(EVertexAttribute, int)} but with 4 component vectors.
     */
    List<IRefAccVec4> extractVector4(EVertexAttribute key, int expectedOutputLength);

    /**
     * Reduce the current segmentation to a flat float array and create a mesh using this and
     * all other information about vertex attribute ordering, their usages, etc.
     */
    Mesh convertToMesh();

    /**
     * @return true if the extraction can progress without issue
     */
    boolean checkExtraction(EVertexAttribute key, int expectedOutputLength, String transformingTo);

    /**
     * Copy the current internal storage into a new IMeshDataTable.
     * The interal cache is not copied.
     */
    IMeshDataTable copy();
}
