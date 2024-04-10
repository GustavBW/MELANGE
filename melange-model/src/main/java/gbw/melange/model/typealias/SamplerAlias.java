package gbw.melange.model.typealias;

import gbw.melange.model.errors.MalformedGLObjectIssue;

public record SamplerAlias(String alias) implements IGLAlias {
    public static final String REQUIRED_PREFIX = "s_";
    public SamplerAlias {
        if (alias.isBlank()){
            throw new MalformedGLObjectIssue("Sampler Alias should probably not be blank.");
        }
        if (!alias.startsWith(REQUIRED_PREFIX)){
            throw new MalformedGLObjectIssue("A Sampler Alias should follow the given glsl conventions. Alias: \"" + alias + "\" does not start with \""+REQUIRED_PREFIX+"\"");
        }
    }
}
