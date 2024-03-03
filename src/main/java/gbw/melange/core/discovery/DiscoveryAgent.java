package gbw.melange.core.discovery;

import gbw.melange.common.annotations.Space;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/**
 * Scans user packages and gathers as much data as possible
 */
public class DiscoveryAgent<T> {

    private final List<Space> userSpaces = new ArrayList<>();

    private final Class<T> userMainClass;
    private Package supposedRootPackageOfUser;
    private final Set<Package> locatedPackages = new HashSet<>();

    public static <T> DiscoveryAgent<T> run(Class<T> mainClassInstance){
        DiscoveryAgent<T> instance = new DiscoveryAgent<>(mainClassInstance);
        instance.findRootPackage();
        instance.gatherUserSpaces();

        return instance;
    }

    public String findRootPackage(){
        Package userPgk = userMainClass.getPackage();
        System.out.println("[DA] user package is: " + userPgk);
        this.supposedRootPackageOfUser = userPgk;
        return userPgk.getName();

    }

    public void gatherUserSpaces(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(supposedRootPackageOfUser.getName());
        context.refresh();

        List<String> spaces = List.of(context.getBeanNamesForAnnotation(Space.class));
        for(String space : spaces){
            System.out.println("[DA] Found Space: " + space);
        }
    }

    private DiscoveryAgent(Class<T> userMainClass){
        assert userMainClass != null;
        this.userMainClass = userMainClass;
    }


}
