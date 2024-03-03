package gbw.melange.core.discovery;

import gbw.melange.common.annotations.Space;
import gbw.melange.common.errors.ClassConfigurationIssue;
import org.reflections.Reflections;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * Scans user packages and gathers as much data as possible
 */
public class DiscoveryAgent<T> {

    private final List<Space> userSpaces = new ArrayList<>();

    private final Class<T> userMainClass;
    private Package supposedRootPackageOfUser;
    private Set<Class<?>> spaces = new HashSet<>();
    private AnnotationConfigApplicationContext programContext = new AnnotationConfigApplicationContext();

    public static <T> DiscoveryAgent<T> run(Class<T> mainClassInstance) throws ClassConfigurationIssue{
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassInstance);
        instance.findRootPackage();
        instance.gatherUserSpaces();

        instance.refresh();
        return instance;
    }

    public String findRootPackage(){
        Package userPgk = userMainClass.getPackage();
        System.out.println("[DA] user package is: " + userPgk);
        this.supposedRootPackageOfUser = userPgk;
        return userPgk.getName();

    }

    public void gatherUserSpaces() throws ClassConfigurationIssue {
        String basePackage = supposedRootPackageOfUser.getName();
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> spaces = reflections.getTypesAnnotatedWith(Space.class);
        System.out.println("[DA].gatherUserSpaces() found: " + spaces.stream().map(Class::toString).toList());

        // Find all classes annotated with @Space
        for (Class<?> spaceClass : spaces) {
            String constructorErrMsg = BeanConstructorValidator.isValidClassForRegistration(spaceClass);
            if(constructorErrMsg != null){
                throw new ClassConfigurationIssue(constructorErrMsg);
            }

            programContext.registerBean(spaceClass);
        }
    }

    private void refresh(){
        programContext.refresh();
    }


    private DiscoveryAgent(Class<T> userMainClass){
        assert userMainClass != null;
        this.userMainClass = userMainClass;

    }


}
