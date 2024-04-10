package gbw.melange.model.typealias;

import gbw.melange.model.errors.MalformedGLObjectIssue;

public record UniformAlias(String alias) implements IGLAlias {
    public static final String REQUIRED_PREFIX = "_u";
    public UniformAlias{
        if (alias.isBlank()){
            throw new MalformedGLObjectIssue("Uniform Alias should probably not be blank.");
        }
        if (!alias.startsWith(REQUIRED_PREFIX)){
            throw new MalformedGLObjectIssue("A Uniform Alias should follow the given glsl conventions. Alias: \"" + alias + "\" does not start with \""+REQUIRED_PREFIX+"\"");
        }
    }
}
