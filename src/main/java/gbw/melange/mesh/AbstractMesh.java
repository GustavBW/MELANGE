package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.mesh.errors.InvalidMeshIssue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Abstract Mesh representation for easier processing, before eventually congregating everything to a standard {@link com.badlogic.gdx.graphics.Mesh}
 * The logic and data manipulation is in {@link MeshDataTable}, this is but a wrapper facilitating caching to allow for a digital-double like situation.
 */
public class AbstractMesh implements IAbstractMesh {
    private static final Logger log = LogManager.getLogger();

    private List<Face> faces;
    private IMeshDataTable dataTable;
    private final Map<Vector3, List<Face>> allFacesOfVert = new HashMap<>();
    private Mesh currentCachedInstance;

    public AbstractMesh(Mesh mesh){
        this.dataTable = MeshDataTable.from(mesh);
    }




    @Override
    public Mesh getMesh(){
        return dataTable.convertToMesh();
    }

    @Override
    public Mesh getMesh(List<EVertexAttribute> vertexAttributeOrdering){
        return null;
    }

}
