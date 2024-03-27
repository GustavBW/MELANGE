package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Disposable;
import gbw.melange.mesh.errors.MeshProcessingIssue;
import gbw.melange.mesh.modifiers.MeshModifier;

import java.util.List;

/**
 * Represents a mesh, albeit with a focus on processing and modification rather than memory and performance.
 * Additionally, facilitates processing the mesh in parallel while still using the earlier copy.
 * Has the same caching mechanisms as the managed shaders so the performance during runtime is almost as good as a plain {@link Mesh}. <br/>
 * Comes in 2 flavours: Modifiable (datatable retained) and unmodifiable (datatable dropped), which can be checked using {@link IManagedMesh#isModifiable()}
 */
public interface IManagedMesh extends Disposable {
    /**
     * Convert the current data to a mesh using the derived or default vertex attribute ordering.
     */
    Mesh getMesh();

    /**
     * Check if a data table is available to run the modifiers on.
     */
    boolean isModifiable();
    void addModifier(MeshModifier modifier);
    void addModifiers(List<MeshModifier> modifiers);
    List<MeshModifier> getModifiers();

    /**
     * Apply any current modifiers registered to the mesh in the order of registration, and store the resulting mesh for further use. <br/>
     * If this is a one time thing, consider dropping the current vertex data table to use less memory. <br/>
     * If you choose to drop the underlying datatable, no further modifications can be done to this mesh
     * and a {@link gbw.melange.mesh.errors.UnmodifiableMeshIssue} will be thrown if attempted. </br>
     * You can also choose to keep the modified data table. This is an exclusive option, and void if you also choose to drop it.
     * @param dropDataTable yes / no, default = no
     * @param modifyDataTable yes / no, default = no
     */
    void applyModifiers(boolean dropDataTable, boolean modifyDataTable) throws MeshProcessingIssue;

    /**
     * Clear the current modifier list.
     */
    void clearModifiers();

    /**
     * Apply the modifiers of this mesh, but store the result as a new instance which is then returned as the result. <br/>
     * You can choose to either copy as unmodifiable by not including a copy of the data table in the copy as well.
     * @param copyDataTable yes/no, default = yes.
     */
    IManagedMesh copyAndApply(boolean copyDataTable) throws MeshProcessingIssue;

}
