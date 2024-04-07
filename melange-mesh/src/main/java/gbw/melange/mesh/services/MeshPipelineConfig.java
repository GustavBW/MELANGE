package gbw.melange.mesh.services;

import gbw.melange.common.mesh.services.IMeshPipelineConfig;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class MeshPipelineConfig implements IMeshPipelineConfig {

    private Set<IMeshPipelineConfig.LogLevel> logLevel = new HashSet<>(Set.of(IMeshPipelineConfig.LogLevel.values()));

}
