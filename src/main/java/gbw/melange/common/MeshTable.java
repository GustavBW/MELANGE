package gbw.melange.common;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

/**
 * All vertex parameters (x, y, z) are always within a -1 to 1 space.
 * This effectively centers all meshes for rotation and positional purposes.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum MeshTable {
    SQUARE(
        new float[] { // x, y, z
            -1f,  -1f,  0, //v0
            1f,   -1f,  0, //v1
            1f,   1f,   0, //v2
            -1f,  1f,   0, //v3
        }, new float[]{ //u, v
            0, 1, 1, 1, 1, 0, 0, 0
        }, new short[] {0, 1, 2, 2, 3, 0} //Tris
    ),
    RHOMBUS(
        new float[] { // x, y, z
            1f, .5f, 0,
            0, -1f, 0,
            1f, 0f, 0,
            0, 1, 0
        }, new float[]{ //u, v
            0, 1, 1, 1, 1, 0, 0, 0
        }, new short[] {0, 3, 1, 1, 3, 2} //Tris
    ),
    EQUILATERAL_TRIANGLE(
        new float[]{
            -0.5f, 0f, 0f, // Bottom left
            0.5f, 0f, 0f, // Bottom right
            0f, 1f, 0f,   // Top
        }, new float[]{
            0f, 0f, // Bottom left
            1f, 0f, // Bottom right
            0.5f, 1f, // Top
        }, new short[]{0, 1, 2}
    ),

    CIRCLE_8(
        createCircleVertices(8), // Radius 1, 32 vertices
        createCircleUVs(8),
        createCircleIndices(8)
    ),
    CIRCLE_32(
        createCircleVertices(32), // Radius 1, 32 vertices
        createCircleUVs(32),
        createCircleIndices(32)
    ),
    CIRCLE_64(
        createCircleVertices(64), // Radius 1, 32 vertices
        createCircleUVs(64),
        createCircleIndices(64)
    );

    private final Mesh mesh;

    MeshTable(float[] coordsXYZ, float[] uvs, short[] indices) {
        // Assuming coordsXYZ.length / 3 == uvs.length / 2, meaning every vertex has a corresponding UV pair
        this.mesh = new Mesh(true, coordsXYZ.length / 3, indices.length,
                VertexAttribute.Position(), VertexAttribute.TexCoords(0));


        // The combined array size should be based on the number of vertices,
        // with each vertex contributing 3 position floats and 2 UV floats
        float[] combinedVerts = new float[(coordsXYZ.length / 3) * 5];
        for (int i = 0, j = 0; i < coordsXYZ.length / 3; i++) {
            // Position data
            combinedVerts[j++] = coordsXYZ[i * 3];     // x
            combinedVerts[j++] = coordsXYZ[i * 3 + 1]; // y
            combinedVerts[j++] = coordsXYZ[i * 3 + 2]; // z

            // UV data
            int uvIndex = i * 2;
            combinedVerts[j++] = uvs[uvIndex];     // u
            combinedVerts[j++] = uvs[uvIndex + 1]; // v
        }

        this.mesh.setVertices(combinedVerts);
        this.mesh.setIndices(indices);
    }


    /**
     * <p>Getter for the field <code>mesh</code>.</p>
     *
     * @return a {@link com.badlogic.gdx.graphics.Mesh} object
     */
    public Mesh getMesh() {
        // Return a reference to the mesh
        // Note: Consider deep-copying here if modifications to the returned mesh could affect its standard definition
        return mesh;
    }

    private static float[] createCircleVertices(int vertexCount) {
        float[] vertices = new float[vertexCount * 3]; // 3 coordinates (x, y, z) per vertex
        double angleStep = 2 * Math.PI / vertexCount;

        for (int i = 0; i < vertexCount; i++) {
            double angle = i * angleStep;
            vertices[i * 3] = (float)Math.cos(angle); // x
            vertices[i * 3 + 1] = (float)Math.sin(angle); // y
            vertices[i * 3 + 2] = 0; // z
        }
        return vertices;
    }

    private static float[] createCircleUVs(int vertexCount) {
        float[] uvs = new float[vertexCount * 2]; // 2 coordinates (u, v) per vertex
        double angleStep = 2 * Math.PI / vertexCount;

        for (int i = 0; i < vertexCount; i++) {
            double angle = i * angleStep;
            uvs[i * 2] = (float)Math.cos(angle) * 0.5f + 0.5f; // u
            uvs[i * 2 + 1] = (float)Math.sin(angle) * 0.5f + 0.5f; // v
        }
        return uvs;
    }

    private static short[] createCircleIndices(int vertexCount) {
        // Creating a fan of triangles, so we need 3 indices per triangle after the first one
        short[] indices = new short[(vertexCount - 2) * 3];
        for (int i = 0; i < vertexCount - 2; i++) {
            indices[i * 3] = 0; // Center of the fan
            indices[i * 3 + 1] = (short)(i + 1);
            indices[i * 3 + 2] = (short)(i + 2);
        }
        return indices;
    }
}
