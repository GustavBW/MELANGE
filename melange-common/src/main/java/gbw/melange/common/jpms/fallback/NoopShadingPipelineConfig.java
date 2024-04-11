package gbw.melange.common.jpms.fallback;

import gbw.melange.common.shading.services.IShadingPipelineConfig;

import java.util.HashSet;
import java.util.Set;

public class NoopShadingPipelineConfig implements IShadingPipelineConfig {
    @Override
    public IShadingPipelineConfig setLoggingAspects(Set<LogLevel> level) {
        return this;
    }

    @Override
    public Set<LogLevel> getLoggingAspects() {
        return new HashSet<>();
    }

    @Override
    public boolean getUseCaching() {
        return false;
    }

    @Override
    public IShadingPipelineConfig useCaching(boolean yesNo) {
        return this;
    }

    @Override
    public IShadingPipelineConfig clearGeneratedContentOnExit(boolean yesNo) {
        return this;
    }

    @Override
    public boolean getClearGeneratedOnExit() {
        return false;
    }

    @Override
    public IShadingPipelineConfig clearGeneratedOnStart(boolean yesNo) {
        return this;
    }

    @Override
    public boolean getClearGeneratedOnStart() {
        return false;
    }
}
