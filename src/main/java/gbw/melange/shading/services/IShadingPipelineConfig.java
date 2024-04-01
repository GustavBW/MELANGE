package gbw.melange.shading.services;

import java.util.Set;

public interface IShadingPipelineConfig {


    enum LogLevel {
        CACHING_STEP_INFO,
        COMPILE_STEP_INFO,
        LIFE_CYCLE_INFO;
    }

    IShadingPipelineConfig setLoggingAspects(Set<LogLevel> level);
    Set<LogLevel> getLoggingAspects();
    boolean getUseCaching();
    IShadingPipelineConfig useCaching(boolean yesNo);
    /**
     * Clear any generated content from the assets/system/generated/shading folder when the program exits.
     *
     * @default false
     */
    IShadingPipelineConfig clearGeneratedContentOnExit(boolean yesNo);

    boolean getClearGeneratedOnExit();
    /**
     * Clear any generated content from the assets/system/generated folder when the program starts.
     *
     * @default true
     */
    IShadingPipelineConfig clearGeneratedOnStart(boolean yesNo);

    boolean getClearGeneratedOnStart();
}
