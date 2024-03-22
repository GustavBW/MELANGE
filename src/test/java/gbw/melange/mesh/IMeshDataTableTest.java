package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.mesh.constants.EVertexAttribute;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IMeshDataTableTest {


    @Test
    void calculateFaces() {
        // Setup
        MeshDataTable table = createSampleMeshDataTable(); // Implement this method to set up a MeshDataTable instance with predefined data
        Map<Vector3, List<Face>> allFacesOfVert = new HashMap<>();

        // Action
        List<Face> faces = table.calculateFaces(allFacesOfVert);

        // Assertion
        assertNotNull(faces);
        assertFalse(faces.isEmpty());
        // Add more assertions to check if the faces are correct, for example:
        // - Check if the number of faces matches expectations
        // - Check if the vertices of the first face are in the expected positions
        // Consider creating a method to validate faces against expected values
    }

    @Test
    void testCalculateFaces() {

    }

    @Test
    void extractVector3() {
        // Setup
        MeshDataTable table = createSampleMeshDataTable(); // This should include EVertexAttribute.POSITION data

        // Action
        List<Vector3> vectors = table.extractVector3(EVertexAttribute.POSITION, 4); // Define expectedOutputLength based on your setup

        // Assertion
        assertNotNull(vectors);
        assertFalse(vectors.isEmpty());
        // Validate the contents of vectors. For example, check the first vector's values:
        assertEquals(-1, vectors.get(0).x, 0.001);
        assertEquals(-1, vectors.get(0).y, 0.001);
        assertEquals(0, vectors.get(0).z, 0.001);
        // Extend this to check more vectors as needed
    }

    @Test
    void extractVector2() {

    }

    @Test
    void extractVector4() {

    }

    @Test
    void convertToMesh() {
        // Setup
        MeshDataTable table = createSampleMeshDataTable(); // This setup should include a variety of vertex attributes

        // Action
        Mesh mesh = table.convertToMesh();

        // Assertion
        assertNotNull(mesh);
        // You'll want to check that the mesh's vertex attributes, vertices, and indices match what was in your MeshDataTable
        // This may involve fetching and comparing mesh data directly, which can get complex depending on the data structure
    }

    @Test
    void checkExtraction() {
        // Setup for a scenario where extraction should succeed
        MeshDataTable table = createSampleMeshDataTable(); // Include specific attribute data

        // Action & Assertion for success
        assertTrue(table.checkExtraction(EVertexAttribute.POSITION, 4, "Vector3"));

        // Setup for a scenario where extraction should fail (e.g., incorrect expectedLength)
        // Action & Assertion for failure
        assertFalse(table.checkExtraction(EVertexAttribute.POSITION, 5, "Vector3"));
    }

    /**
     * 4 verticies
     * @return
     */
    private MeshDataTable createSampleMeshDataTable() {
        LinkedHashMap<EVertexAttribute, float[]> vertexDataTable = new LinkedHashMap<>();

        // Assuming positions, UVs, and normals are interlaced in the `vertices` array
        vertexDataTable.put(EVertexAttribute.POSITION, new float[]{
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f
        });
        vertexDataTable.put(EVertexAttribute.UV, new float[]{
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f
        });
        vertexDataTable.put(EVertexAttribute.NORMAL, new float[]{
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f
        });

        short[] indices = {0, 1, 2, 2, 3, 0};
        int vertexCount = 4; // Total number of vertices

        return MeshDataTable.from(vertexDataTable, vertexCount, indices);
    }
}