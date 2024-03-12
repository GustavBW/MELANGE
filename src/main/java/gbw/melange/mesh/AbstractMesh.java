package gbw.melange.mesh;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import gbw.melange.common.gl_wrappers.EVertexAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Mesh representation for easier processing
 */
public class AbstractMesh {

    //Faces
    //Edges
    //Vec3 pos

    //Further attribute table
    final float[][][] attributeTable = new float[][][]{
            {
                {
                    //pos, x, y, z
                },
                {
                    //uv, u, v
                }
            }
    };
    /**
     * Ordered by field usage
     */
    public static final EVertexAttribute[] DEFAULT_ATTRIBUTE_ORDERING = new EVertexAttribute[]{EVertexAttribute.POSITION, EVertexAttribute.COLOR_UNPACKED, EVertexAttribute.COLOR_PACKED, EVertexAttribute.NORMAL, EVertexAttribute.TEX_COORDS, EVertexAttribute.BONE_WEIGHT, EVertexAttribute.TANGENT, EVertexAttribute.BI_NORMAL};
    private static record Face(Vector3 v1, Vector3 v2, Vector3 v3){}
    private final List<Vector3> verts = new ArrayList<>();
    private final List<Face> faces = new ArrayList<>();

    public AbstractMesh(float[] xyz, int[] faces){
        if(xyz.length % 3 != 0){
            throw new RuntimeException("An abstract mesh is always 3D. The Z parameter might be 0, but must still be declared");
        }
        if(faces.length % 3 != 0){
            throw new RuntimeException("Face declarations for an abstract mesh required and must declare triangles");
        }

        for(int i = 0; i + 3 < xyz.length; i += 3){
            verts.add(new Vector3(xyz[i], xyz[i + 1], xyz[i + 2]));
        }

        //Face declarations is assumed to be in order
        for(int i = 0; i + 3 < faces.length; i += 3){
            this.faces.add(new Face(verts.get(i), verts.get(i + 1), verts.get(i + 2)));
        }
    }

    public Mesh toMesh(){
        return null;
    }

    public Mesh toMesh(List<EVertexAttribute> vertexAttributeOrdering){
        return null;
    }

}
