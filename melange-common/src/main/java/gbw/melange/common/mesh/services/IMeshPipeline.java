package gbw.melange.common.mesh.services;

import gbw.melange.common.mesh.IManagedMesh;
import gbw.melange.common.mesh.errors.InvalidMeshIssue;
import gbw.melange.common.mesh.errors.MeshProcessingIssue;

public interface IMeshPipeline {

    void register(IManagedMesh mesh);
    void beginProcessing() throws MeshProcessingIssue, InvalidMeshIssue;
}
