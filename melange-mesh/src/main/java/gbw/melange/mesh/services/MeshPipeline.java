package gbw.melange.mesh.services;

import gbw.melange.common.mesh.IManagedMesh;
import gbw.melange.common.mesh.errors.InvalidMeshIssue;
import gbw.melange.common.mesh.errors.MeshProcessingIssue;
import gbw.melange.common.mesh.services.IMeshPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Exact same setup as the {@link gbw.melange.common.shading.services.IShaderPipeline}
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
        while(unProcessed.peek() != null){
            IManagedMesh mesh = unProcessed.poll();
            if(mesh == null || !mesh.isModifiable()){
                throw new InvalidMeshIssue("Unable to process " + mesh + " as its unmodifiable.");
            }
            executor.submit(() -> {
                try {
                    process(mesh);
                } catch (MeshProcessingIssue e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static void process(IManagedMesh mesh) throws MeshProcessingIssue {
        mesh.applyModifiers(false, false);
    }
}
