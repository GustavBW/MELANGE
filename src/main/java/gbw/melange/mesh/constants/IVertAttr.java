package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;

public interface IVertAttr {
    boolean equals(VertexAttribute vAttr);

    VertexAttribute asVA();

    int compCount();

    String alias();

    int usage();
}
