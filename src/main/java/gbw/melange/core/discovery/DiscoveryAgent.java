package gbw.melange.core.discovery;

import gbw.melange.common.elementary.ISpace;
import gbw.melange.common.errors.ClassConfigurationIssue;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.common.hooks.OnRender;
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
    private Reflections rootPackageReflections;
    private final AnnotationConfigApplicationContext programContext = new AnnotationConfigApplicationContext();

    //Hooks
    private final List<OnRender> onRenderHookImpls = new ArrayList<>();
    private final List<OnInit> onInitHookImpls = new ArrayList<>();
    private final List<ISpace> userSpaces = new ArrayList<>();

    public static <T> DiscoveryAgent<T> locateButDontInstantiate(Class<T> mainClassType) throws ClassConfigurationIssue{
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassType);
        instance.findRootPackage();

        instance.gatherUserSpaces();
        instance.addUserMainClassToTheMix();
        //Hooks
        instance.gatherUserOnRenderImpl();
        instance.gatherUserOnInitImpl();
        return instance;
    }

    private void gatherUserOnInitImpl() throws ClassConfigurationIssue  {
        Set<Class<? extends OnInit>> onInitImpls = rootPackageReflections.getSubTypesOf(OnInit.class);
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
        Set<Class<? extends OnRender>> onRenderImpls = rootPackageReflections.getSubTypesOf(OnRender.class);
        log.info("Found onRender hooks: " + onRenderImpls.stream().map(Class::toString).toList());
        for (Class<? extends OnRender> onRenderImpl : onRenderImpls) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(onRenderImpl);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(onRenderImpl + ": " + constructorErrMsg);
            }

            programContext.registerBean(onRenderImpl);
        }
    }

    private void addUserMainClassToTheMix() throws ClassConfigurationIssue {
        if(userMainClass == null){
            throw new ClassConfigurationIssue("null is not a valid main class.");
        }
        String mainClassErr = BeanConstructorValidator.isValidClassForRegistration(userMainClass);
        if(mainClassErr != null){
            throw new ClassConfigurationIssue("The provided main class: " + mainClassErr);
        }
        programContext.registerBean(userMainClass);
    }

    public void findRootPackage(){
        this.supposedRootPackageOfUser = userMainClass.getPackage();
        this.rootPackageReflections = new Reflections(supposedRootPackageOfUser.getName());
    }

    public void gatherUserSpaces() throws ClassConfigurationIssue {
        Set<Class<? extends ISpace>> spaces = rootPackageReflections.getSubTypesOf(ISpace.class);
        log.info("Found spaces: " + spaces.stream().map(Class::toString).toList());
        for (Class<? extends ISpace> spaceClass : spaces) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(spaceClass);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(spaceClass + ": " + constructorErrMsg);
            }

            programContext.registerBean(spaceClass);
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
    public List<ISpace> getUserSpaces() {
        return userSpaces;
    }
    private void refresh(){
        programContext.refresh();
        onRenderHookImpls.addAll(programContext.getBeansOfType(OnRender.class).values());
        onInitHookImpls.addAll(programContext.getBeansOfType(OnInit.class).values());
        userSpaces.addAll(programContext.getBeansOfType(ISpace.class).values());
    }



    private DiscoveryAgent(Class<T> userMainClass){
        this.userMainClass = userMainClass;

    }



}
