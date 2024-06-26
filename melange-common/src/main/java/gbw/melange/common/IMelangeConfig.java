package gbw.melange.common;

import gbw.melange.common.mesh.services.IMeshPipelineConfig;
import gbw.melange.common.shading.services.IShadingPipelineConfig;

import java.util.Set;

public interface IMelangeConfig {

    enum LogLevel {
        BOOT_SEQ_INFO,
        SPRING_REFLECT_INFO,
        PIPELINE_STATUS,
        ELEMENT_UPDATES,
        VIEW_INFO,
        HOOKS,
        IMPROPER_API_USAGE,
        PIPELINE_ISSUES;
    }

    void resolve();
    /**
     * Cache shaders on disk and sample as textures to increase performance.
     *
     * @default true
     */
    IMelangeConfig useCaching(boolean yesNo);

    boolean getUseCaching();

    /**
     * Gain further information about any number of topics related to the framework runtime and boot sequence.
     *
     * @default all
     */
    IMelangeConfig setLoggingAspects(Set<LogLevel> values);

    Set<LogLevel> getLoggingAspects();

    IMelangeConfig setShadingConfig(IShadingPipelineConfig shadingConfig);
    IShadingPipelineConfig getShadingConfig();
    IMelangeConfig setMeshConfig(IMeshPipelineConfig meshConfig);
    IMeshPipelineConfig getMeshConfig();


    /**
     * Clear any generated content from the assets/system/generated folder when the program exits.
     *
     * @default false
     */
    IMelangeConfig clearGeneratedContentOnExit(boolean yesNo);

    boolean getClearGeneratedOnExit();

    /**
     * Clear any generated content from the assets/system/generated folder when the program starts.
     *
     * @default true
     */
    IMelangeConfig clearGeneratedOnStart(boolean yesNo);

    boolean getClearGeneratedOnStart();

    IMelangeConfig enableGLDebug(boolean yesNo);

    boolean getEnableGLDebug();


}
