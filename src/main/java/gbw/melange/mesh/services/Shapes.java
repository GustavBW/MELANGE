package gbw.melange.mesh.services;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.mesh.IManagedMesh;

/**
 * Definition for the Shapes api facilitating procedural meshes, loading and modifying existing meshes, and more.
 */
public interface Shapes {

    IManagedMesh square();
    IManagedMesh any(Mesh mesh);


}
