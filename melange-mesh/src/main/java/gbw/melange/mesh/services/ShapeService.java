package gbw.melange.mesh.services;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.ManagedMesh;
import gbw.melange.mesh.IManagedMesh;
import gbw.melange.mesh.constants.MeshTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShapeService implements Shapes {

    private final IMeshPipeline pipeline;

    @Autowired
    public ShapeService(IMeshPipeline pipeline){
        this.pipeline = pipeline;
    }

    @Override
    public IManagedMesh square() {
        IManagedMesh newSquare = new ManagedMesh(MeshTable.SQUARE.getMesh());
        pipeline.register(newSquare);
        return newSquare;
    }

    @Override
    public IManagedMesh any(Mesh mesh) {
        IManagedMesh customInstance = new ManagedMesh(MeshTable.SQUARE.getMesh());
        pipeline.register(customInstance);
        return customInstance;
    }
}
