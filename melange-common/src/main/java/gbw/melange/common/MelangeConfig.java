package gbw.melange.common;

import gbw.melange.common.jpms.SPI;
import gbw.melange.common.jpms.fallback.NoopMeshPipelineConfig;
import gbw.melange.common.jpms.fallback.NoopShadingPipelineConfig;
import gbw.melange.common.jpms.SPILocator;
import gbw.melange.common.mesh.services.IMeshPipelineConfig;
import gbw.melange.common.shading.services.IShadingPipelineConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
//This is manually registered, meaning if an instance is defined and provided on MelangeApplication.run()
//that instance is preserved and distributed as if the result of the standard BeanProvider<IMelangeConfig>
@Configuration
public class MelangeConfig implements IMelangeConfig {
    private static final Logger log = LogManager.getLogger();
    private boolean cachingEnabled = true;
    private Set<LogLevel> logLevel = new HashSet<>(Set.of(LogLevel.VIEW_INFO, LogLevel.HOOKS, LogLevel.IMPROPER_API_USAGE, LogLevel.PIPELINE_ISSUES));
    private boolean glDebugEnabled = false;
    private boolean clearGeneratedOnExit = false;
    private boolean clearGeneratedOnStart = true;
    private IShadingPipelineConfig shadingConfig = SPILocator.loadService(SPI.of(IShadingPipelineConfig.class, NoopShadingPipelineConfig::new));
    private IMeshPipelineConfig meshConfig = SPILocator.loadService(SPI.of(IMeshPipelineConfig.class, NoopMeshPipelineConfig::new));

    @Override
    public String toString(){
        return "MelangeConfig {cachingEnabled: " + cachingEnabled + ", logLevel: " + logLevel + ", clearGenOnExit: " + clearGeneratedOnExit + ", clearGenOnStart: " + clearGeneratedOnStart + ", shadingConfig: " + shadingConfig + ", meshConfig: " + meshConfig + "}";
    }

    public void resolve(){

        if(!logLevel.contains(LogLevel.PIPELINE_STATUS)){
            log.trace("resolve | Removing pipeline info");
            shadingConfig.setLoggingAspects(new HashSet<>());

        }

        log.debug(this);
    }

    @Override
    public IMelangeConfig useCaching(boolean yesNo){
       this.cachingEnabled = yesNo;
       shadingConfig.useCaching(yesNo);
       return this;
    }
    @Override
    public boolean getUseCaching(){
        return cachingEnabled;
    }

    @Override
    public IMelangeConfig setLoggingAspects(Set<LogLevel> values){
        this.logLevel = values;
        return this;
    }
    @Override
    public Set<LogLevel> getLoggingAspects(){
        return logLevel;
    }

    @Override
    public IMelangeConfig setShadingConfig(IShadingPipelineConfig shadingConfig) {
        if (shadingConfig == null) return this;

        this.shadingConfig = shadingConfig;
        return this;
    }


    @Override
    public IMelangeConfig setMeshConfig(IMeshPipelineConfig meshConfig) {
        if (meshConfig == null) return this;

        this.meshConfig = meshConfig;
        return this;
    }


    @Override
    public IMelangeConfig clearGeneratedContentOnExit(boolean yesNo){
        this.clearGeneratedOnExit = yesNo;
        shadingConfig.clearGeneratedContentOnExit(yesNo);
        return this;
    }
    @Override
    public boolean getClearGeneratedOnExit(){
        return clearGeneratedOnExit;
    }

    @Override
    public IMelangeConfig clearGeneratedOnStart(boolean yesNo){
        this.clearGeneratedOnStart = yesNo;
        shadingConfig.clearGeneratedOnStart(yesNo);
        return this;
    }
    @Override
    public boolean getClearGeneratedOnStart(){
        return clearGeneratedOnStart;
    }

    @Override
    public IMelangeConfig enableGLDebug(boolean yesNo){
        this.glDebugEnabled = yesNo; //Doesn't work rn
        return this;
    }
    @Override
    public boolean getEnableGLDebug(){
        return glDebugEnabled;
    }

    @Bean
    @Override
    public IShadingPipelineConfig getShadingConfig() {
        return shadingConfig;
    }

    @Bean
    @Override
    public IMeshPipelineConfig getMeshConfig() {
        return meshConfig;
    }

}
