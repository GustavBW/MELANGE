package gbw.melange.model;

public enum SourceType {
    /**
     * Source of mesh vertex data.
     */
    VERTEX_BUFFER,
    /**
     * Source of mesh face / index / tris data.
     */
    ELEMENT_BUFFER,
    VERTEX_ATTRIBUTE,
    GLSL_UNIFORM;
}
