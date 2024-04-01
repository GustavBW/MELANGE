package gbw.melange.shading.services;

import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ShadingPipelineConfig implements IShadingPipelineConfig {

    private Set<LogLevel> logLevel = new HashSet<>(Set.of(LogLevel.values()));
    private boolean usingCaching = true;
    private boolean clearGeneratedOnStart = false;
    private boolean clearGeneratedOnExit = false;

    @Override
    public IShadingPipelineConfig setLoggingAspects(Set<LogLevel> level) {
        this.logLevel = level;
        return this;
    }

    @Override
    public Set<LogLevel> getLoggingAspects() {
        return logLevel;
    }

    @Override
    public boolean getUseCaching() {
        return usingCaching;
    }

    @Override
    public IShadingPipelineConfig useCaching(boolean yesNo) {
        this.usingCaching = yesNo;
        return this;
    }

    @Override
    public IShadingPipelineConfig clearGeneratedContentOnExit(boolean yesNo) {
        this.clearGeneratedOnExit = yesNo;
        return this;
    }

    @Override
    public boolean getClearGeneratedOnExit() {
        return clearGeneratedOnExit;
    }

    @Override
    public IShadingPipelineConfig clearGeneratedOnStart(boolean yesNo) {
        this.clearGeneratedOnStart = yesNo;
        return this;
    }

    @Override
    public boolean getClearGeneratedOnStart() {
        return clearGeneratedOnStart;
    }
}
