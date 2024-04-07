package gbw.melange.mesh.modifiers;

import gbw.melange.mesh.formatting.IMeshDataTable;

public interface MeshModifier {

    IMeshDataTable apply(IMeshDataTable table);
    MeshModifier copy();


}
