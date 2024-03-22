package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.mesh.operations.MeshModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Abstract Mesh representation for easier processing, before eventually congregating everything to a standard {@link com.badlogic.gdx.graphics.Mesh}
 * The logic and data manipulation is in {@link MeshDataTable}, this is but a wrapper facilitating caching to allow for a digital-double like situation.
 */
public class ManagedMesh implements IManagedMesh {
    private static final Logger log = LogManager.getLogger();

    private List<Face> faces;
    private IMeshDataTable dataTable;
    private final Map<Vector3, List<Face>> allFacesOfVert = new HashMap<>();
    private Mesh currentCachedInstance;
    private final Object instanceLock = new Object();
    private List<MeshModifier> modifiers = new ArrayList<>();

    public ManagedMesh(Mesh mesh){
        this.dataTable = MeshDataTable.from(mesh);
    }

    @Override
    public Mesh getMesh(){
        synchronized (instanceLock) {
            if(currentCachedInstance == null){
                currentCachedInstance = dataTable.convertToMesh();
            }
        }

        return currentCachedInstance;
    }

    @Override
    public void addModifier(MeshModifier modifier) {
        this.modifiers.add(modifier);
    }

    @Override
    public List<MeshModifier> getModifiers() {
        return modifiers;
    }


    @Override
    public void dispose() {
        if(currentCachedInstance != null){
            currentCachedInstance.dispose();
        }
    }
}
