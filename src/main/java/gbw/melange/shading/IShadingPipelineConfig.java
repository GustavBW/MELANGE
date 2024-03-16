package gbw.melange.shading;

import java.util.Set;

public interface IShadingPipelineConfig {

    enum PipelineLogLevel{
        CACHING_STEP_INFO,
        COMPILE_STEP_INFO,
        LIFE_CYCLE_INFO;
    }

    IShadingPipelineConfig setLogLevel(Set<PipelineLogLevel> level);
    Set<PipelineLogLevel> getLogLevel();
}
