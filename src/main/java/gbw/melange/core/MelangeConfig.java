package gbw.melange.core;

//TODO: Move to Common
public class MelangeConfig {

    boolean cachingEnabled = true;
    float logLevel = 2;
    boolean glDebugEnabled = false;


    /**
     * Cache shaders on disk and sample as textures to increase performance.
     */
    public MelangeConfig useCaching(boolean yesNo){
       this.cachingEnabled = yesNo;
       return this;
    }

    /**
     * Level with fallthrough. Anything higher than the provided level is also included
     * <pre>
     * 0: Anything and everything
     * 1: System info
     *      1.1: Boot sequence performance info
     *      1.2: Spring and reflections info
     *      1.3: Pipeline status
     * 2: User info
     *      2.1: User views
     *      2.2: User hooks
     * 3: System warnings
     *      3.1: Improper API usage
     *      3.2: Pipeline issues
     * </pre>
     */
    public MelangeConfig setLogLevel(float value){
        //TODO: Implement this
        this.logLevel = value;
        return this;
    }

    public MelangeConfig enableGLDebug(boolean yesNo){
        this.glDebugEnabled = yesNo;
        return this;
    }

}
