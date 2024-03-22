package gbw.melange.mesh.services;

import gbw.melange.mesh.IManagedMesh;
import gbw.melange.mesh.errors.InvalidMeshIssue;
import gbw.melange.mesh.errors.MeshProcessingIssue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Exact same setup as the {@link gbw.melange.shading.services.IShaderPipeline}
 */
@Service
public class MeshPipeline implements IMeshPipeline {
    private static final Logger log = LogManager.getLogger();
    private final Queue<IManagedMesh> unProcessed = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(16);

    @Override
    public void register(IManagedMesh mesh) {
        unProcessed.add(mesh);
    }

    @Override
    public void beginProcessing() throws MeshProcessingIssue, InvalidMeshIssue {
        log.warn("Procedural mesh processing not implemented.");
    }
}
