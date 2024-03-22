package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.mesh.operations.MeshModifier;

import java.util.List;

/**
 * Represents a mesh, albeit with a focus on processing and modification rather than memory and performance.
 * Additionally, facilitates processing meshes in parallel. Has the same caching mechanisms as the managed shaders.
 */
public interface IManagedMesh extends Disposable {
    /**
     * Convert the current data to a mesh using the derived or default vertex attribute ordering.
     */
    Mesh getMesh();
    void addModifier(MeshModifier modifier);
    List<MeshModifier> getModifiers();

}
