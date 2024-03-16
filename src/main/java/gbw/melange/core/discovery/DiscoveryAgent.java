package gbw.melange.core.discovery;

import gbw.melange.common.IMelangeConfig;
import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.ISpaceProvider;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.errors.ViewConfigurationIssue;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.CoreRootMarker;
import gbw.melange.core.elementary.ISpaceRegistry;
import gbw.melange.mesh.IMeshPipelineConfig;
import gbw.melange.shading.IShadingPipelineConfig;
import gbw.melange.shading.ShadingRootMarker;
import org.reflections.Reflections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * Scans user packages and gathers as much data as possible.
 * The FBI of Melange
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class DiscoveryAgent<T> {

    private static final Logger log = LogManager.getLogger();
    private final Class<T> userMainClass;
    private Reflections userRootReflections, systemRootReflections;
    private final AnnotationConfigApplicationContext programContext = new AnnotationConfigApplicationContext();
    private final Map<Class<?>, View> userViewInformation = new HashMap<>();
    //Hooks
    private final List<OnRender> onRenderHookImpls = new ArrayList<>();
    private final List<OnInit<?>> onInitHookImpls = new ArrayList<>();
    private final IMelangeConfig config;
    /**
     * <p>locateButDontInstantiate.</p>
     *
     * @param mainClassType a {@link java.lang.Class} object
     * @param <T> a T class
     * @return a {@link gbw.melange.core.discovery.DiscoveryAgent} object
     * @throws gbw.melange.common.errors.ClassConfigurationIssue if any.
     */
    public static <T> DiscoveryAgent<T> locateButDontInstantiate(Class<T> mainClassType, IMelangeConfig config) throws ClassConfigurationIssue{
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassType, config);
        //System
        instance.setUpReflections();
        instance.registerConfigurations(config);
        instance.registerSpaceRegistry();
        instance.registerSpaceProviders();
        instance.registerCoreSpringServices();
        //User
        instance.registerUserMainClass();
        instance.registerUserViews();
        //Hooks
        instance.gatherUserOnRenderImpl();
        instance.gatherUserOnInitImpl();

        return instance;
    }

    private void registerSpaceRegistry() throws ClassConfigurationIssue {
        Set<Class<? extends ISpaceRegistry>> spaceRegistries = systemRootReflections.getSubTypesOf(ISpaceRegistry.class);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.SPRING_REFLECT_INFO)){
            log.info("Found registries: " + spaceRegistries.stream().map(Class::toString).toList());
        }

        for (Class<? extends ISpaceRegistry> registry : spaceRegistries){
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(registry);
            if (constructorErrMsg != null){
                throw new ClassConfigurationIssue(registry + ": " + constructorErrMsg);
            }

            programContext.registerBean(registry);
        }
    }
    /**
     * <p>instantiateAndPrepare.</p>
     *
     * @throws gbw.melange.common.errors.ViewConfigurationIssue if any.
     */
    public void instantiateAndPrepare() throws ViewConfigurationIssue {
        refresh();
    }
    /**
     * <p>getOnRenderList.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<OnRender> getOnRenderList(){
        return onRenderHookImpls;
    }
    /**
     * <p>Getter for the field <code>onInitHookImpls</code>.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<OnInit<?>> getOnInitHookImpls(){
        return onInitHookImpls;
    }
    /**
     * <p>Getter for the field <code>userViewInformation</code>.</p>
     *
     * @return a {@link java.util.Map} object
     */
    public Map<Class<?>, View> getUserViewInformation(){
        return userViewInformation;
    }

    private void registerCoreSpringServices() {
        programContext.scan(CoreRootMarker.class.getPackageName());
        programContext.scan(ShadingRootMarker.class.getPackageName());
    }
    private void registerSpaceProviders() throws ClassConfigurationIssue {
        //Damn, var is actually useful for avoiding "oh no our type erasure forces you to use raw types - we're still going to make a yellow line anyways" - issues
        var providers = systemRootReflections.getSubTypesOf(ISpaceProvider.class);
        providers = TypeFilter.retainInstantiable(providers);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.SPRING_REFLECT_INFO)) {
            log.info("Found space providers: " + providers.stream().map(Class::toString).toList());
        }

        for (var provider : providers) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(provider);
            if (constructorErrMsg != null){
                throw new ClassConfigurationIssue(provider + ": " + constructorErrMsg);
            }

            programContext.registerBean(provider);
        }
    }
    private void gatherUserOnInitImpl() throws ClassConfigurationIssue  {
        Set<Class<? extends OnInit>> onInitImpls = userRootReflections.getSubTypesOf(OnInit.class);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.HOOKS)) {
            log.info("Found OnInit hooks: " + onInitImpls.stream().map(Class::toString).toList());
        }

        for (Class<? extends OnInit> onInitImpl : onInitImpls){
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(onInitImpl);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(onInitImpl + ": " + constructorErrMsg);
            }

            programContext.registerBean(onInitImpl);
        }
    }
    private void gatherUserOnRenderImpl() throws ClassConfigurationIssue {
        Set<Class<? extends OnRender>> onRenderImpls = userRootReflections.getSubTypesOf(OnRender.class);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.HOOKS)) {
            log.info("Found onRender hooks: " + onRenderImpls.stream().map(Class::toString).toList());
        }

        for (Class<? extends OnRender> onRenderImpl : onRenderImpls) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(onRenderImpl);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(onRenderImpl + ": " + constructorErrMsg);
            }

            programContext.registerBean(onRenderImpl);
        }
    }
    private void registerUserMainClass() throws ClassConfigurationIssue {
        if(userMainClass == null){
            throw new ClassConfigurationIssue("null is not a valid main class.");
        }
        String mainClassErr = BeanConstructorValidator.isValidClassForRegistration(userMainClass);
        if(mainClassErr != null){
            throw new ClassConfigurationIssue("The provided main class: " + mainClassErr);
        }
        programContext.registerBean(userMainClass);
    }
    private void setUpReflections(){
        Package supposedRootPackageOfUser = userMainClass.getPackage();
        this.userRootReflections = new Reflections(supposedRootPackageOfUser.getName());
        this.systemRootReflections = new Reflections(CoreRootMarker.class.getPackage().getName());
    }

    private void registerConfigurations(IMelangeConfig config) {
        // Register the existing config instance as a bean
        programContext.registerBean("melangeConfig", IMelangeConfig.class, () -> config);

        // Register sub-configurations separately
        // This allows shading and mesh to have 0 deps on Core or Common, but still receive their respective configs
        //programContext.registerBean("shaderPipelineConfig", IShadingPipelineConfig.class, config::getShadingConfig);
        //programContext.registerBean("meshPipelineConfig", IMeshPipelineConfig.class, config::getMeshConfig);
    }

    private void registerUserViews() throws ClassConfigurationIssue {
        Set<Class<?>> views = userRootReflections.getTypesAnnotatedWith(View.class);
        views = TypeFilter.retainInstantiable(views);

        if (config.getLogLevel().contains(IMelangeConfig.LogLevel.VIEW_INFO)) {
            log.info("Found views: " + views.stream().map(Class::toString).toList());
        }

        for (Class<?> viewType : views) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(viewType);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(viewType + ": " + constructorErrMsg);
            }

            userViewInformation.put(viewType, viewType.getAnnotation(View.class));
            programContext.registerBean(viewType);
        }
    }


    private void refresh() {
        programContext.refresh();
        onRenderHookImpls.addAll(programContext.getBeansOfType(OnRender.class).values());
        //Java's Type Erasure really coming in handy here
        onInitHookImpls.addAll((Collection<OnInit<?>>) (Collection<?>) programContext.getBeansOfType(OnInit.class).values());
    }



    private DiscoveryAgent(Class<T> userMainClass, IMelangeConfig config){
        this.userMainClass = userMainClass;
        this.config = config;
    }


    /**
     * <p>getContext.</p>
     *
     * @return a {@link org.springframework.context.ApplicationContext} object
     */
    public ApplicationContext getContext() {
        return programContext;
    }
}
