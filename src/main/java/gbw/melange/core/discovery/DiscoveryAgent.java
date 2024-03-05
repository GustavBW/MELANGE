package gbw.melange.core.discovery;

import gbw.melange.common.annotations.View;
import gbw.melange.common.elementary.space.ISpace;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.common.hooks.OnRender;
import gbw.melange.core.CoreRootMarker;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * Scans user packages and gathers as much data as possible
 */
public class DiscoveryAgent<T> {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryAgent.class);
    private final Class<T> userMainClass;
    private Package supposedRootPackageOfUser;
    private Reflections userRootReflections, systemRootReflections;
    private final AnnotationConfigApplicationContext programContext = new AnnotationConfigApplicationContext();
    //Hooks
    private final List<OnRender> onRenderHookImpls = new ArrayList<>();
    private final List<OnInit> onInitHookImpls = new ArrayList<>();
    private final Map<Class<?>, Integer> userViewOrdering = new HashMap<>();

    public static <T> DiscoveryAgent<T> locateButDontInstantiate(Class<T> mainClassType) throws ClassConfigurationIssue{
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassType);
        //System
        log.info("______________________SYSTEM_______________________");
        instance.setUpReflections();
        instance.registerUserAvailableSpaces();
        //User
        log.info("______________________USER_______________________");
        instance.registerUserMainClass();
        instance.registerUserViews();
        //Hooks
        instance.gatherUserOnRenderImpl();
        instance.gatherUserOnInitImpl();

        return instance;
    }
    private void registerUserAvailableSpaces() throws ClassConfigurationIssue {
        Set<Class<? extends ISpace>> spaceTypes = systemRootReflections.getSubTypesOf(ISpace.class);
        spaceTypes = TypeFilter.onlyBeanables(spaceTypes);
        log.info("Found space types: " + spaceTypes.stream().map(Class::toString).toList());
        for (Class<? extends ISpace> spaceType : spaceTypes) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(spaceType);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(spaceType + ": " + constructorErrMsg);
            }

            programContext.registerBean(spaceType);
        }
    }
    private void gatherUserOnInitImpl() throws ClassConfigurationIssue  {
        Set<Class<? extends OnInit>> onInitImpls = userRootReflections.getSubTypesOf(OnInit.class);
        log.info("Found OnInit hooks: " + onInitImpls.stream().map(Class::toString).toList());
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
        log.info("Found onRender hooks: " + onRenderImpls.stream().map(Class::toString).toList());
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
        this.supposedRootPackageOfUser = userMainClass.getPackage();
        this.userRootReflections = new Reflections(supposedRootPackageOfUser.getName());
        this.systemRootReflections = new Reflections(CoreRootMarker.class.getPackage().getName());
    }
    private void registerUserViews() throws ClassConfigurationIssue {
        Set<Class<?>> views = userRootReflections.getTypesAnnotatedWith(View.class);
        views = TypeFilter.onlyBeanables(views);
        log.info("Found views: " + views.stream().map(Class::toString).toList());
        for (Class<?> viewType : views) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(viewType);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(viewType + ": " + constructorErrMsg);
            }

            View viewAnnotationOfView = viewType.getAnnotation(View.class);
            int layerOfView = viewAnnotationOfView.layer();
            userViewOrdering.put(viewType, layerOfView);

            programContext.registerBean(viewType);
        }
    }

    public void instatiateAndPrepare() {
        refresh();
    }
    public List<OnRender> getOnRenderList(){
        return onRenderHookImpls;
    }
    public List<OnInit> getOnInitHookImpls(){
        return onInitHookImpls;
    }
    private void refresh(){
        programContext.refresh();
        onRenderHookImpls.addAll(programContext.getBeansOfType(OnRender.class).values());
        onInitHookImpls.addAll(programContext.getBeansOfType(OnInit.class).values());

    }



    private DiscoveryAgent(Class<T> userMainClass){
        this.userMainClass = userMainClass;

    }



}
