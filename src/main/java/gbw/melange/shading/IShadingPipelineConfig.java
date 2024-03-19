package gbw.melange.shading;

import java.util.Set;

public interface IShadingPipelineConfig {

    enum LogLevel {
        CACHING_STEP_INFO,
        COMPILE_STEP_INFO,
        LIFE_CYCLE_INFO;
    }

    IShadingPipelineConfig setLoggingAspects(Set<LogLevel> level);
    Set<LogLevel> getLoggingAspects();
}
