package gbw.melange.common.mesh.modifiers;

import gbw.melange.common.mesh.formatting.IMeshDataTable;

public interface MeshModifier {

    IMeshDataTable apply(IMeshDataTable table);
    MeshModifier copy();


}
