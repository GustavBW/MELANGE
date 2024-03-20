package gbw.melange.common;

import gbw.melange.mesh.services.IMeshPipelineConfig;
import gbw.melange.mesh.services.MeshPipelineConfig;
import gbw.melange.shading.services.IShadingPipelineConfig;
import gbw.melange.shading.services.ShadingPipelineConfig;
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
    private IShadingPipelineConfig shadingConfig = new ShadingPipelineConfig();
    private IMeshPipelineConfig meshConfig = new MeshPipelineConfig();

    public void resolve(){

        if(!logLevel.contains(LogLevel.PIPELINE_STATUS)){
            log.trace("resolve | Removing pipeline info");
            shadingConfig.setLoggingAspects(new HashSet<>());

        }
    }

    @Override
    public IMelangeConfig useCaching(boolean yesNo){
       this.cachingEnabled = yesNo;
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

    @Bean
    @Override
    public IShadingPipelineConfig getShadingConfig() {
        return shadingConfig;
    }

    @Override
    public IMelangeConfig setMeshConfig(IMeshPipelineConfig meshConfig) {
        if (meshConfig == null) return this;

        this.meshConfig = meshConfig;
        return this;
    }

    @Bean
    @Override
    public IMeshPipelineConfig getMeshConfig() {
        return meshConfig;
    }

    @Override
    public IMelangeConfig clearGeneratedContentOnExit(boolean yesNo){
        this.clearGeneratedOnExit = yesNo;
        return this;
    }
    @Override
    public boolean getClearGeneratedOnExit(){
        return clearGeneratedOnExit;
    }

    @Override
    public IMelangeConfig clearGeneratedOnStart(boolean yesNo){
        this.clearGeneratedOnStart = yesNo;
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

}
