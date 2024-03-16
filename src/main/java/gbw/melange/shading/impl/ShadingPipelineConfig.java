package gbw.melange.shading.impl;

import gbw.melange.shading.IShadingPipelineConfig;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ShadingPipelineConfig implements IShadingPipelineConfig {

    private Set<IShadingPipelineConfig.PipelineLogLevel> logLevel = new HashSet<>(Set.of(IShadingPipelineConfig.PipelineLogLevel.values()));

    @Override
    public IShadingPipelineConfig setLogLevel(Set<PipelineLogLevel> level) {
        this.logLevel = level;
        return this;
    }

    @Override
    public Set<PipelineLogLevel> getLogLevel() {
        return logLevel;
    }
}
