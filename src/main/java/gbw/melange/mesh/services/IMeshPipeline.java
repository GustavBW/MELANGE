package gbw.melange.mesh.services;

import gbw.melange.mesh.IManagedMesh;
import gbw.melange.mesh.errors.InvalidMeshIssue;
import gbw.melange.mesh.errors.MeshProcessingIssue;

public interface IMeshPipeline {

    void register(IManagedMesh mesh);
    void beginProcessing() throws MeshProcessingIssue, InvalidMeshIssue;
}
