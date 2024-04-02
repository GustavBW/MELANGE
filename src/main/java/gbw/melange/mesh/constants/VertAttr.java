package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;

public record VertAttr(int compCount, String alias, int usage) {

    public boolean equals(VertexAttribute vAttr){
        return vAttr.usage == this.usage()
                && vAttr.numComponents == this.compCount()
                && vAttr.alias.equals(this.alias());
    }

    public VertexAttribute asVA(){
        return new VertexAttribute(this.usage(), this.compCount(), this.alias());
    }
}
