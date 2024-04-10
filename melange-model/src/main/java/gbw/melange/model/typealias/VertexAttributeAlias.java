package gbw.melange.model.typealias;

import gbw.melange.model.errors.MalformedGLObjectIssue;

/**
 * Type-alias with inbuilt error check for some kind of vertex attribute.
 * Will throw a {@link MalformedGLObjectIssue} exception if the alias is invalid.
 * @param alias
 */
public record VertexAttributeAlias(String alias) implements IGLAlias {
    public static final String REQUIRED_PREFIX_CONST = "a_";
    /**
     * When passing data from vertex shader to fragment (or with a geometry shader in-between), these would be denoted as "varying" in glsl.
     * As it's not an externally bound uniform, not is it a constant.
     */
    public static final String REQUIRED_PREFIX_VARYING = "v_";
    public VertexAttributeAlias {
        if (alias.isBlank()){
            throw new MalformedGLObjectIssue("Attribute Alias should probably not be blank.");
        }
        if (!alias.startsWith(REQUIRED_PREFIX_CONST)){ //TODO: the convention for any varying attribute (passing vertex data from vertex shader to fragment) is v_ and is also a valid attribute alias
            throw new MalformedGLObjectIssue("A VertexAttribute Alias should follow the given glsl conventions. Alias: \"" + alias + "\" does not start with \""+REQUIRED_PREFIX_CONST+"\"");
        }
    }
}
