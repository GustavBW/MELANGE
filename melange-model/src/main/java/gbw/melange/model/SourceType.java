package gbw.melange.model;

import com.badlogic.gdx.graphics.GL30;

public enum SourceType {
    /**
     * Source of mesh vertex data.
     */
    VERTEX_BUFFER(GL30.GL_VERTEX_ARRAY_BINDING),
    /**
     * Source of mesh face / index / tris data.
     */
    ELEMENT_BUFFER(-1),
    VERTEX_ATTRIBUTE(-1),
    GLSL_UNIFORM(-1),
    NONE(-1);

    public final int glValue;
    SourceType(int glValue){
        this.glValue = glValue;
    }
}
