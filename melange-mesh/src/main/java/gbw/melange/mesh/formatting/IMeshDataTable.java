package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.constants.VertAttr;
import gbw.melange.mesh.constants.IVertAttr;
import gbw.melange.common.errors.Error;
import gbw.melange.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.mesh.formatting.slicing.ISliceVec4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IMeshDataTable (variable-type table), is a System memory representation of the vertex data for a given Mesh and is for utility-, and storage
 * purposes only. It, in itself, assures 100% data retention on any operation, however, it is not hotpath safe.
 * Also, it allows the underlying data to stay as simple as possible (float arrays), while presenting it
 * in a more manageable way which can be dictated whenever data is retrieved. (Although always as Slices, see {@link IFloatSlice} and its many children)
 *<br/><br/>
 * Also also, it's NOT THREADSAFE. To reduce the amount of allocations, all retrievals are cached, and this cache
 * is synchronized with any other modifications immediately to keep the memory footprint near-constant. So please keep an eye on any loose threads.
 * <br/><br/>
 * The general structure can be visualized as: <br/>
 * <pre>
 *                  | vert1    | vert2     | vert3     | ...
 * Position (3comp) | .0,.0,.0 | .0, 1f,.0 | 1f, 1f,.0 | ...
 * UV       (2comp) | .0,.0,   | 0., 1f,   | 1f, 1f,   | ...
 *         .
 *         .
 *         .
 * </pre>
 *
 * @author GustavBW
 */
public interface IMeshDataTable {

    /**
     * Get all vertex attributes currently present in the table.
     */
    Set<IVertAttr<? extends IFloatSlice>> getVertexAttributes();

    /**
     * Delete an attribute from the table and risk the wrath of the GC.
     * Any current data is extracted and returned in the specified format,
     * however that is the only reference to the former data of the attribute at that point. <br/>
     *
     * @param attr Attribute to delete.
     * @return Any current data in the specified format, or an empty list if no data existed.
     */
    <T extends IFloatSlice> List<T> dropAttribute(IVertAttr<T> attr);

    int getVertexCount();

    /**
     * Copy the current data for some attribute and return it as an array.
     * If no such attribute is present, the array will be empty.
     * The copying is done through {@link Arrays#copyOf(float[], int)} which is {@link Object#clone()} in this instance -pretty fast, but not hotpath safe.
     */
    float[] copyAttributeData(IVertAttr<?> attr);

    int[] getIndicies();

    /**
     * Add the data from another {@link IMeshDataTable} onto the end of this as an irreversible process.
     * Indices will not be modified, however the underlying float buffers will be extended.
     * This also updates all lists of retrieved slices currently cached. </br>
     * Will error if:
     * <ul>
     *     <li>The table to be added does not contain all attributes of this table, or the other way around.</li>
     * </ul>
     * @return {@link Error#NONE} or an error describing what went wrong.
     */
    Error add(IMeshDataTable other);

    /**
     * Add a new attribute to the mesh and update existing slice representations. If this attribute already exists, replace the current data.
     * Addition or replacement will happen in the vertex order known to the IMeshDataTable.
     * <ul>
     *     <li>If an insufficient amount of values are provided, an error is returned.</li>
     *     <li>If too much data is provided, the remainder is ignored.</li>
     *     <li>If the startIndex is not 0, and no data is already available for the attribute, an error is returned.</li>
     * </ul>
     * For a less stringent method see {@link IMeshDataTable#addOrReplaceAttribute(IVertAttr, int, float[], float)}
     * @param attr What attribute to alter the data of, or set new data for
     * @param data The actual float values to insert.
     * @param startIndex Where to start IN TERMS OF VERTEX NUMBER which is dependent on the {@link VertAttr#compCount()}
     * @return an {@link Error} if any, or {@link Error#NONE}
     */
    <T extends IFloatSlice> Error addOrReplaceAttribute(IVertAttr<T> attr, int startIndex, float[] data);

    /**
     * Add a new attribute to the mesh. If this attribute already exists, replace the current data.
     * Addition or replacement will happen in the vertex order known to the {@link IMeshDataTable}.
     * <ul>
     *     <li>If an insufficient amount of values is provided, the rest are set to the fill value.</li>
     *     <li>If too much data is provided, the remainder is ignored.</li>
     *     <li>If the startIndex is not 0, and no data is already available for the attribute,
     *     the prior indexes are filled with the fill value.</li>
     * </ul>
     * For a more stringent method see {@link IMeshDataTable#addOrReplaceAttribute(IVertAttr, int, float[])}.
     * @param attr What attribute to alter the data of, or set new data for
     * @param data The actual float values to insert.
     * @param startIndex Where to start IN TERMS OF VERTEX NUMBER which is dependent on the {@link VertAttr#compCount()}
     * @return an {@link Error} if any, or {@link Error#NONE}
     */
    <T extends IFloatSlice> Error addOrReplaceAttribute(IVertAttr<T> attr, int startIndex, float[] data, float fillValue);

    /**
     * See {@link IMeshDataTable#calculateFaces(Map)} but without the generated lookup table
     */
    List<Face> calculateFaces();
    /**
     * Extract the triangles as {@link Face} based on the indicies of the original mesh
     *
     * @param allFacesOfVert fill this provided map to create a lookup table between a given vertex position (vector 3, x, y, z) and all faces this vertex is a part of
     */
    List<Face> calculateFaces(Map<ISliceVec3, List<Face>> allFacesOfVert);

    /**
     * Extract the data for a given vertex attribute from the table in the repressentation specified by the vertex attribute itself.
     * If the key itself fails validation checks, or no data is present, an empty list is returned.
     * Use {@link IMeshDataTable#checkExtraction(IVertAttr, int)} or {@link MultiTypeTableChecker#checkKey(IVertAttr)} for further information.
     *
     * @param attr The vertex attribute to extract data for.
     * @param <T> Anything that is, or can be percieved as an instance of {@link IFloatSlice}
     *           or subtype of an implementation of, or ...
     * @return A list of {@link IFloatSlice} instances representing the extracted data. If the
     *         attribute is not found or the key fails validation checks, this list will be empty.
     *         Any extracted list of slices is, and will be, the only instances until further modification
     *         as to keep the memory footprint contained.
     */
    <T extends IFloatSlice> List<T> extract(IVertAttr<T> attr);

    /**
     * Same as {@link IMeshDataTable#extract(IVertAttr)} but deep-copies everything but the source float arrays.
     * This is registered as an extraction, so it does go through the cache, but the
     * retrieved list is completely new instances.
     */
    <T extends IFloatSlice> List<T> extractCopy(IVertAttr<T> attr);

    /**
     * Reduce the current segmentation to a flat float array and create a mesh using this and
     * all other information about vertex attribute ordering, their usages, etc.
     */
    Mesh convertToMesh();

    /**
     * @return {@link Error#NONE} if the extraction can progress without issue else a description of the issue
     */
    Error checkExtraction(IVertAttr<?> key, int expectedOutputLength);

    /**
     * Copy the current internal storage into a new IMeshDataTable.
     * The interal cache is not copied.
     */
    IMeshDataTable copy();
}
