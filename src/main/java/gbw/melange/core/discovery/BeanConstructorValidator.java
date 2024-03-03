package gbw.melange.core.discovery;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class BeanConstructorValidator {

    /**
     * Checks if a given class can be instantiated or not in a bean-way
     * @param clazz
     * @return An error message or null;
     */
    public static String isValidClassForRegistration(Class<?> clazz) {

        // Check for valid constructors
        String hasNoArgs = hasAvailableNoArgsConstructor(clazz);
        String hasAutoWired = hasAutowiredConstructor(clazz);

        if(hasNoArgs == null && hasAutoWired == null){
            return null;
        }
        if(hasNoArgs != null && hasAutoWired != null){
            return hasNoArgs + " OR" + hasAutoWired;
        }

        return null;
    }

    public static String hasAutowiredConstructor(Class<?> spaceClass) {
        String latestIssue = null;
        for (Constructor<?> constructor : spaceClass.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                // Check if the @Autowired constructor is public or protected
                if (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers())) {
                    return null;
                }else{
                    latestIssue = " has a constructor marked as @Autowired, but it is not accessible. Consider making it public";
                }
            }
        }
        if(latestIssue != null){
            return latestIssue;
        }
        return " does not expose a public constructor marked with @Autowired";
    }

    /**
     * Whether a class exposes a public or protected no-args constructor or not.
     * @return An error message or null.
     */
    public static String hasAvailableNoArgsConstructor(Class<?> clazz) {
        try {
            Constructor<?> noArgsConstructor = clazz.getDeclaredConstructor();
            // Check if the no-args constructor is public or protected
            if (Modifier.isPublic(noArgsConstructor.getModifiers()) || Modifier.isProtected(noArgsConstructor.getModifiers())) {
                return null;
            }else{
                return " does have a no-args constructor but it is not accessible. Consider making it public.";
            }
        } catch (NoSuchMethodException e) {
            // No-arg constructor does not exist, proceed to check for @Autowired constructors
            return " does not expose a public no-args constructor";
        }
    }

}
