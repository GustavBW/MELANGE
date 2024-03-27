package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.mesh.errors.MeshProcessingIssue;
import gbw.melange.mesh.errors.UnmodifiableMeshIssue;
import gbw.melange.mesh.modifiers.MeshModifier;
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

    private final List<MeshModifier> modifiers = new ArrayList<>();
    private volatile Mesh currentCachedInstance;
    private final Object instanceLock = new Object();

    public ManagedMesh(Mesh mesh){
        this.dataTable = MeshDataTable.from(mesh);
    }
    public ManagedMesh(IMeshDataTable table){
        this.dataTable = table;
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
    public boolean isModifiable() {
        return dataTable != null;
    }

    @Override
    public void addModifier(MeshModifier modifier) {
        this.modifiers.add(modifier);
    }

    @Override
    public void addModifiers(List<MeshModifier> modifiers) {
        this.modifiers.addAll(modifiers);
    }

    @Override
    public List<MeshModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void applyModifiers(boolean dropDataTable, boolean modifyDataTable) {
        if(dataTable == null){
            throw new UnmodifiableMeshIssue("Unable to apply modifiers because the data table has been dropped.");
        }
        IMeshDataTable tableToWriteTo = dataTable;
        if(!modifyDataTable){
            tableToWriteTo = dataTable.copy();
        }

        for (MeshModifier modifier : modifiers){
            log.debug("Applying " + modifier + " to " + this);
            tableToWriteTo = modifier.apply(tableToWriteTo);
        }

        synchronized (instanceLock) {
            currentCachedInstance = tableToWriteTo.convertToMesh();
        }

        if(dropDataTable) this.dataTable = null;
    }

    @Override
    public void clearModifiers() {
        this.modifiers.clear();
    }

    @Override
    public IManagedMesh copyAndApply(boolean copyDataTable) throws MeshProcessingIssue {
        IManagedMesh copy = new ManagedMesh(this.dataTable.copy());
        copy.addModifiers(this.modifiers.stream().map(MeshModifier::copy).toList());
        copy.applyModifiers(false, false);
        return copy;
    }


    @Override
    public void dispose() {
        if(currentCachedInstance != null){
            currentCachedInstance.dispose();
        }
    }
}
