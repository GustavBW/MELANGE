package gbw.melange.core.discovery;

import gbw.melange.common.annotations.Space;
import gbw.melange.common.errors.ClassConfigurationIssue;
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
    private AnnotationConfigApplicationContext programContext = new AnnotationConfigApplicationContext();

    //Hooks
    private List<OnRender> onRenderHookImpl = new ArrayList<>();

    public static <T> DiscoveryAgent<T> locateButDontInstantiate(Class<T> mainClassInstance) throws ClassConfigurationIssue{
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassInstance);
        instance.findRootPackage();
        instance.gatherUserSpaces();
        instance.addUserMainClassToTheMix();
        instance.gatherCustomProcessors();
        return instance;
    }

    private void gatherCustomProcessors() throws ClassConfigurationIssue {
        String basePackage = supposedRootPackageOfUser.getName();
        Reflections reflections = new Reflections(basePackage);

        Set<Class<? extends OnRender>> processors = reflections.getSubTypesOf(OnRender.class);
        log.info("Found onRender hook implementations: " + processors.stream().map(Class::toString).toList());
        for (Class<? extends OnRender> processorClass : processors) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(processorClass);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(processorClass + constructorErrMsg);
            }

            programContext.registerBean(processorClass);
        }
    }

    private void addUserMainClassToTheMix() throws ClassConfigurationIssue {
        if(userMainClass == null){
            throw new ClassConfigurationIssue("null is not a valid main class parameter.");
        }
        String mainClassErr = BeanConstructorValidator.isValidClassForRegistration(userMainClass);
        if(mainClassErr != null){
            throw new ClassConfigurationIssue("The provided main class should have a no args constructor or an autowired one. (" + userMainClass + ")");
        }
        programContext.registerBean(userMainClass);
    }

    public void findRootPackage(){
        Package userPgk = userMainClass.getPackage();
        this.supposedRootPackageOfUser = userPgk;
    }

    public void gatherUserSpaces() throws ClassConfigurationIssue {
        String basePackage = supposedRootPackageOfUser.getName();
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> spaces = reflections.getTypesAnnotatedWith(Space.class);
        log.info("Found spaces: " + spaces.stream().map(Class::toString).toList());

        // Find all classes annotated with @Space
        for (Class<?> spaceClass : spaces) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(spaceClass);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(spaceClass + constructorErrMsg);
            }

            programContext.registerBean(spaceClass);
        }
    }

    public void instatiateAndPrepare() {
        refresh();
    }
    public List<OnRender> getOnRenderList(){
        return onRenderHookImpl;
    }

    private void refresh(){
        programContext.refresh();
        onRenderHookImpl = programContext.getBeansOfType(OnRender.class).values().stream().toList();
    }



    private DiscoveryAgent(Class<T> userMainClass){
        this.userMainClass = userMainClass;

    }


}
