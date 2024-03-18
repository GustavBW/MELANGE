package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.mesh.errors.InvalidMeshIssue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Abstract Mesh representation for easier processing, before eventually congregating everything to a standard {@link com.badlogic.gdx.graphics.Mesh}
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class AbstractMesh {
    private static final Logger log = LogManager.getLogger();
    public static AbstractMesh from(Mesh mesh) throws InvalidMeshIssue {
        if (mesh == null) {
            throw new InvalidMeshIssue("Mesh is null");
        }

        //Raw data
        float[] vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];
        mesh.getVertices(vertices);

        short[] indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);

        VertexAttributes attrs = mesh.getVertexAttributes();
        List<EVertexAttribute> mappedAttr = EVertexAttribute.from(attrs);
        if(mappedAttr.size() != attrs.size()){
            throw new InvalidMeshIssue("Unable to map vertex attributes");
        }

        Map<EVertexAttribute, Integer> totalAttrOffset = new HashMap<>();
        int totalOffsetOfPrevious = 0;
        for(EVertexAttribute attr : mappedAttr){
            totalAttrOffset.put(attr, totalOffsetOfPrevious);
            totalOffsetOfPrevious += attr.componentCount();
        }

        //Insertion Ordered
        final LinkedHashMap<EVertexAttribute, float[]> vertexDataTable = new LinkedHashMap<>();
        // Assuming `totalAttrOffset` and `mappedAttr` are correctly populated
        for(EVertexAttribute attr : mappedAttr){
            // Initialize the array to hold data for all vertices of this attribute
            float[] data = new float[mesh.getNumVertices() * attr.componentCount()];
            vertexDataTable.put(attr, data);
        }

        // The size of a single vertex's data in floats, assuming it's the sum of the components of all attributes
        int vertexSize = totalOffsetOfPrevious; // This is effectively the stride

        for (int vertexIndex = 0; vertexIndex < mesh.getNumVertices(); vertexIndex++) {
            // Calculate the starting index for this vertex's data
            int baseIndex = vertexIndex * vertexSize;
            for (EVertexAttribute attr : mappedAttr) {
                if (attr.componentCount() <= 0) continue;

                // Get the array where we'll store this attribute's data
                float[] attributeData = vertexDataTable.get(attr);
                // Calculate the starting index for this attribute's data within the vertex
                int attrStartIndex = baseIndex + totalAttrOffset.get(attr);
                // Extract the data and store it
                System.arraycopy(vertices, attrStartIndex, attributeData, vertexIndex * attr.componentCount(), attr.componentCount());
            }
        }

        return new AbstractMesh(vertexDataTable, indices, vertices.length);
    }
    private List<Face> faces;
    private List<Vector3> positionData;
    private final Map<Vector3, List<Face>> allFacesOfVert = new HashMap<>();
    private int currentVertCount = 0;

    /**
     * <p>Constructor for AbstractMesh.</p>
     */
    public AbstractMesh(LinkedHashMap<EVertexAttribute,float[]> dataTable, short[] indicies, int vertexCount) throws InvalidMeshIssue {
        this.currentVertCount = vertexCount;
        if(!dataTable.containsKey(EVertexAttribute.POSITION)){
            positionData = new ArrayList<>();
            faces = new ArrayList<>();
        } else {
            positionData = extractVector3(dataTable, EVertexAttribute.POSITION, vertexCount);
            faces = calculateFaces(positionData, indicies, allFacesOfVert);
        }
    }



    public static List<Face> calculateFaces(List<Vector3> positionData, short[] indices, Map<Vector3, List<Face>> allFacesOfVert) {
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < indices.length; i += 3) {
            // Ensure there are at least 3 indices left to form a face
            if (i + 2 < indices.length) {
                Vector3 v1 = positionData.get(indices[i] & 0xFFFF); // & 0xFFFF converts short to unsigned
                Vector3 v2 = positionData.get(indices[i + 1] & 0xFFFF);
                Vector3 v3 = positionData.get(indices[i + 2] & 0xFFFF);
                Face face = new Face(v1, v2, v3);
                allFacesOfVert.computeIfAbsent(v1, k -> new ArrayList<>()).add(face);
                allFacesOfVert.computeIfAbsent(v2, k -> new ArrayList<>()).add(face);
                allFacesOfVert.computeIfAbsent(v3, k -> new ArrayList<>()).add(face);
                faces.add(face);
            }
        }
        return faces;
    }

    /**
     * Extract a row as a list of 3-dimensional vectors.
     * @param dataTable Vertex data table
     * @param key To extract as Vector3's
     * @param expectedOutputLength -1 to ignore, or actual value for additional error checking.
     */
    public static List<Vector3> extractVector3(Map<EVertexAttribute,float[]> dataTable, EVertexAttribute key, int expectedOutputLength){
        checkExtraction(dataTable, key, expectedOutputLength, "Vector3", 3);

        float[] data = dataTable.get(key);
        List<Vector3> vectors = new ArrayList<>();
        int maxSafeIndex = (data.length / 3) * 3;

        // First iteration: Guaranteed not to go out of bounds
        for (int i = 0; i < maxSafeIndex; i += 3) {
            vectors.add(new Vector3(data[i], data[i + 1], data[i + 2]));
        }

        if (maxSafeIndex < data.length) {
            float x = data[maxSafeIndex];
            float y = maxSafeIndex + 1 < data.length ? data[maxSafeIndex + 1] : 0f;
            float z = 0f; // The third component is always zero because we're out of bounds
            vectors.add(new Vector3(x, y, z));
        }

        return vectors;
    }
    public static List<Vector2> extractVector2(Map<EVertexAttribute, float[]> dataTable, EVertexAttribute key, int expectedOutputLength) {
        checkExtraction(dataTable, key, expectedOutputLength, "Vector2", 2);

        float[] data = dataTable.get(key);
        List<Vector2> vectors = new ArrayList<>();
        for (int i = 0; i < data.length; i += 2) {
            vectors.add(new Vector2(data[i], data[i + 1]));
        }
        return vectors;
    }
    public static List<Vector4> extractVector4(Map<EVertexAttribute, float[]> dataTable, EVertexAttribute key, int expectedOutputLength) {
        checkExtraction(dataTable, key, expectedOutputLength, "Vector4", 4);

        float[] data = dataTable.get(key);
        List<Vector4> vectors = new ArrayList<>();
        for (int i = 0; i < data.length; i += 4) {
            vectors.add(new Vector4(data[i], data[i + 1], data[i + 2], data[i + 3]));
        }
        return vectors;
    }
    private static void checkExtraction(Map<EVertexAttribute, float[]> dataTable, EVertexAttribute key, int expectedOutputLength, String transformingTo, int compCount){
        if(!dataTable.containsKey(key)){
            log.debug("Tried to extract " + key + " from " + dataTable + " as " + transformingTo + ", but no entry was present");
        }
        final int entryLen = dataTable.get(key).length;
        if (expectedOutputLength != -1 && entryLen / compCount != expectedOutputLength) {
            throw new IllegalArgumentException("Available data for " + key + " len: " + entryLen + " cannot be safely transformed to a list of "+transformingTo+". Input -1 for expectedOutputLength to disable this check.");
        }
    }

    public Mesh toMesh(){
        return null;
    }


    public Mesh toMesh(List<EVertexAttribute> vertexAttributeOrdering){
        return null;
    }

}
