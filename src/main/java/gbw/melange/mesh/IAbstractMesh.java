package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.constants.EVertexAttribute;

import java.util.List;

/**
 * Represents a mesh, albeit with a focus on processing and modification rather than memory and performance.
 */
public interface IAbstractMesh {
    /**
     * Convert the current data to a mesh using the derived or default vertex attribute ordering.
     */
    Mesh getMesh();

    /**
     * Convert the current data to a mesh following a specific provided vertex attribute ordering.
     */
    Mesh getMesh(List<EVertexAttribute> ordering);

}
