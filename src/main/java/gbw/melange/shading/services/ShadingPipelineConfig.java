package gbw.melange.shading.services;

import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ShadingPipelineConfig implements IShadingPipelineConfig {

    private Set<LogLevel> logLevel = new HashSet<>(Set.of(LogLevel.values()));

    @Override
    public IShadingPipelineConfig setLoggingAspects(Set<LogLevel> level) {
        this.logLevel = level;
        return this;
    }

    @Override
    public Set<LogLevel> getLoggingAspects() {
        return logLevel;
    }
}
