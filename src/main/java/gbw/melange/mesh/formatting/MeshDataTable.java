package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import gbw.melange.mesh.constants.EVertexAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
//Doesn't currently support write operations, although that'd probably be nice
public class MeshDataTable implements IMeshDataTable {
    private static final Logger log = LogManager.getLogger();

    private final LinkedHashMap<EVertexAttribute, float[]> vertexDataTable;
    private short[] indicies;
    private int vertexCount = 0;

    public static MeshDataTable from(Mesh mesh)  {
        if (mesh == null) {
            log.warn("Mesh is null");
            return from(new LinkedHashMap<>(), 0, new short[0]);
        }

        //Raw data
        float[] vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];
        mesh.getVertices(vertices);

        short[] indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);

        VertexAttributes attrs = mesh.getVertexAttributes();
        List<EVertexAttribute> mappedAttr = EVertexAttribute.convert(attrs);

        if(mappedAttr.size() != attrs.size()){
            log.warn("Unable to map vertex attributes");
            return from(new LinkedHashMap<>(), 0, new short[0]);
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

        return from(vertexDataTable, mesh.getNumVertices(), indices);
    }
    public static MeshDataTable from(LinkedHashMap<EVertexAttribute, float[]> dataTable, int vertexCount, short[] indicies){
        return new MeshDataTable(dataTable, vertexCount, indicies);
    }

    private MeshDataTable(LinkedHashMap<EVertexAttribute, float[]> dataTable, int vertexCount, short[] indicies){
        this.indicies = indicies;
        this.vertexCount = vertexCount;
        this.vertexDataTable = dataTable;
    }

    @Override
    public List<Face> calculateFaces() {
        List<Ref.Vec3> positionData = extractVector3(EVertexAttribute.POSITION, vertexCount);
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < indicies.length; i += 3) {
            // Ensure there are at least 3 indices left to form a face
            if (i + 2 < indicies.length) {
                Ref.Vec3 v1 = positionData.get(indicies[i] & 0xFFFF); // & 0xFFFF converts short to unsigned
                Ref.Vec3 v2 = positionData.get(indicies[i + 1] & 0xFFFF);
                Ref.Vec3 v3 = positionData.get(indicies[i + 2] & 0xFFFF);
                faces.add(new Face(v1, v2, v3));
            }
        }
        return faces;
    }
    private static final Function<Ref.Vec3, List<Face>> faceListProvider = k -> new ArrayList<>();
    @Override
    public List<Face> calculateFaces(Map<Ref.Vec3, List<Face>> allFacesOfVert) {
        List<Face> faces = calculateFaces();
        for (Face face : faces){
            allFacesOfVert.computeIfAbsent(face.v0(), faceListProvider).add(face);
            allFacesOfVert.computeIfAbsent(face.v1(), faceListProvider).add(face);
            allFacesOfVert.computeIfAbsent(face.v2(), faceListProvider).add(face);
        }
        return faces;
    }
    @Override
    public List<Ref.Vec3> extractVector3(EVertexAttribute key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        float[] data = vertexDataTable.get(key);
        List<Ref.Vec3> vectors = new ArrayList<>();

        // First iteration: Guaranteed not to go out of bounds
        for (int i = 0; i < data.length; i += 3) {
            vectors.add(new Ref.Vec3(data, i, 3));
        }

        return vectors;
    }
    @Override
    public List<Ref.Vec2> extractVector2(EVertexAttribute key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector2")) {
                return new ArrayList<>();
            }
        }

        float[] data = vertexDataTable.get(key);
        List<Ref.Vec2> vectors = new ArrayList<>();
        for (int i = 0; i < data.length; i += 2) {
            vectors.add(new Ref.Vec2(data, i, 2));
        }

        return vectors;
    }
    @Override
    public List<Ref.Vec4> extractVector4(EVertexAttribute key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector4")) {
                return new ArrayList<>();
            }
        }

        float[] data = vertexDataTable.get(key);
        List<Ref.Vec4> vectors = new ArrayList<>();
        for (int i = 0; i < data.length; i += 4) {
            vectors.add(new Ref.Vec4(data, i, 4));
        }
        return vectors;
    }

    @Override
    public Mesh convertToMesh() {
        // First, calculate the total size of a single vertex by summing the component counts
        Set<EVertexAttribute> keyset = vertexDataTable.keySet();
        int vertexSizeInFloats = 0;
        for(EVertexAttribute attr : keyset){
            vertexSizeInFloats += attr.componentCount();
        }

        // Then, calculate the size of the flattened array
        float[] vertices = new float[vertexSizeInFloats * vertexCount];

        // Iterate through each attribute, adding its data to the correct position in the vertices array
        int currentOffset = 0;
        for (Map.Entry<EVertexAttribute, float[]> entry : vertexDataTable.entrySet()) {
            float[] attributeData = entry.getValue();
            int componentCount = entry.getKey().componentCount();

            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                System.arraycopy(attributeData, vertexIndex * componentCount, vertices,
                        vertexIndex * vertexSizeInFloats + currentOffset, componentCount);
            }
            currentOffset += componentCount;
        }
        // Create the array of VertexAttributes for the Mesh
        VertexAttribute[] vertexAttributes = EVertexAttribute.convert(keyset);

        // Finally, create the Mesh
        Mesh out = new Mesh(true, vertexCount, indicies.length, vertexAttributes);
        out.setVertices(vertices);
        out.setIndices(indicies);

        return out;
    }

    @Override
    public boolean checkExtraction(EVertexAttribute key, int expectedOutputLength, String transformingTo) {
        if (!vertexDataTable.containsKey(key)) {
            MeshDataTable.log.debug("Tried to extract " + key + " from " + vertexDataTable + " as " + transformingTo + ", but no entry was present");
            return false;
        }
        final int entryLen = vertexDataTable.get(key).length;
        if (entryLen / key.componentCount() != expectedOutputLength) {
            MeshDataTable.log.warn("Available data for " + key + " len: " + entryLen + " cannot be safely transformed to a list of " + transformingTo + ". Input -1 for expectedOutputLength to disable this check.");
            return false;
        }
        return true;
    }

    @Override
    public IMeshDataTable copy() {
        LinkedHashMap<EVertexAttribute, float[]> copy = new LinkedHashMap<>();
        for(EVertexAttribute key : vertexDataTable.keySet()){
            float[] original = vertexDataTable.get(key);
            final float[] entryCopy = new float[original.length];
            System.arraycopy(original, 0, entryCopy, 0, original.length);
            copy.put(key, entryCopy);
        }
        final short[] indiciesCopy = new short[indicies.length];
        System.arraycopy(this.indicies, 0, indiciesCopy, 0, this.indicies.length);

        return MeshDataTable.from(copy, this.vertexCount, indiciesCopy);
    }

}
