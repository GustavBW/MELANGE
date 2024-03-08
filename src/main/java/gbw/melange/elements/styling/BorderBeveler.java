package gbw.melange.elements.styling;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.common.elementary.styling.IBevelOperation;

import java.util.ArrayList;
import java.util.List;

public class BorderBeveler implements IBevelOperation {

    public static final BorderBeveler DEFAULT = new BorderBeveler(BevelConfig.DEFAULT);
    public static final BorderBeveler NOOP = new BorderBeveler(BevelConfig.NONE);
    private int vertNum;
    private double angleThreshDeg;
    private double absRelDist;

    public BorderBeveler(BevelConfig config){
        vertNum = config.subdivs();
        angleThreshDeg = config.angleThreshold();
        absRelDist = config.absoluteRelativeDistance();
    }

    @Override
    public void setVertices(int num) {
        this.vertNum = num;
    }

    @Override
    public void setAngleThreshold(double angleDeg) {
        this.angleThreshDeg = angleDeg;
    }

    @Override
    public void setWidth(double absoluteRelativeDistance) {
        this.absRelDist = absoluteRelativeDistance;
    }

    private record Vertex(double x, double y, double z, double u, double v, double r, double g, double b, double a){}
    @Override
    public Mesh apply(Mesh originalMesh) {
        // Early exit if configuration does not require action
        if (vertNum <= 0 || angleThreshDeg <= 0 || absRelDist <= 0) return originalMesh.copy(true);

        // Step 1: Identify vertices to bevel based on the angle threshold
        List<Vertex> verticesToBevel = findVerticesToBevel(originalMesh, angleThreshDeg);

        // Initialize a structure to hold the new geometry
        MeshBuilder meshBuilder = new MeshBuilder();
        /**
        // Step 2: For each vertex to bevel, calculate new vertex positions for the bevel
        for (Vertex vertex : verticesToBevel) {
            List<Vertex> newVertices = calculateBevelGeometryAroundVertex(vertex, vertNum, absRelDist, originalMesh);
            meshBuilder.addBeveledVertex(vertex, newVertices);

        }
        */
        // Step 3: Reconstruct the mesh with the new vertices
        Mesh beveledMesh = meshBuilder.end();

        return beveledMesh;
    }

    private List<Vertex> findVerticesToBevel(Mesh originalMesh, double angleThreshDeg) {
        final int numVerts = originalMesh.getNumVertices();
        final float[] verts = originalMesh.getVertices(new float[numVerts]);

        return null;
    }

    private List<Vertex> extractVertices(Mesh mesh) {
        int numVertices = mesh.getNumVertices();
        int vertexSize = mesh.getVertexSize() / 4; // Vertex size in bytes divided by 4 to get float count
        float[] vertices = new float[numVertices * vertexSize];
        mesh.getVertices(vertices);

        List<Vertex> vertexList = new ArrayList<>(numVertices);
        for (int i = 0; i < vertices.length; i += vertexSize) {
            // Assuming the vertex attributes are ordered as x, y, z, u, v, r, g, b, a
            Vertex vertex = new Vertex(vertices[i], vertices[i + 1], vertices[i + 2],
                    vertices[i + 3], vertices[i + 4], vertices[i + 5],
                    vertices[i + 6], vertices[i + 7], vertices[i + 8]);
            vertexList.add(vertex);
        }
        return vertexList;
    }
}
