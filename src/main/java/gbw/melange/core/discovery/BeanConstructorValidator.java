package gbw.melange.core.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class BeanConstructorValidator {

    private static final Logger log = LoggerFactory.getLogger(BeanConstructorValidator.class);

    /**
     * Checks if a given class can be instantiated or not in a bean-way
     * @param clazz
     * @return An error message or null;
     */
    public static String isValidClassForRegistration(Class<?> clazz) {
        Constructor<?> noArgsConstructor = locateNoArgsConstructor(clazz);
        Constructor<?> autowiredConstructor = locateAutoWiredConstructor(clazz);

        boolean noArgsConstructorValid = noArgsConstructor != null && isConstructorVisible(noArgsConstructor) && !doesValidConstructorThrow(noArgsConstructor);
        boolean autowiredConstructorValid = autowiredConstructor != null && isConstructorVisible(autowiredConstructor) && !doesValidConstructorThrow(autowiredConstructor);

        // Both constructors are missing
        if (noArgsConstructor == null && autowiredConstructor == null) {
            return "A class must have either a no-args constructor or an @Autowired constructor.";
        }

        // Both constructors are present but not necessarily valid
        if (noArgsConstructor != null && autowiredConstructor != null) {
            if (!noArgsConstructorValid && !autowiredConstructorValid) {
                return "Both no-args and @Autowired constructors are present but neither is valid. Ensure at least one is public/protected and does not throw exceptions.";
            }
            // If one is valid, it's acceptable; no need for further action.
        } else if (noArgsConstructor != null) {
            // Only no-args constructor is present
            if (!noArgsConstructorValid) {
                if (!isConstructorVisible(noArgsConstructor)) {
                    return "The no-args constructor must be public or protected.";
                }
                if (doesValidConstructorThrow(noArgsConstructor)) {
                    return "The no-args constructor should not throw exceptions. Consider using the OnInit hook for error handling.";
                }
            }
        } else {
            // Only @Autowired constructor is present
            if (!autowiredConstructorValid) {
                if (!isConstructorVisible(autowiredConstructor)) {
                    return "The @Autowired constructor must be public or protected.";
                }
                if (doesValidConstructorThrow(autowiredConstructor)) {
                    return "The @Autowired constructor should not throw exceptions. Consider using the OnInit hook for error handling.";
                }
            }
        }

        // No errors found
        return null;
    }

    public static boolean doesValidConstructorThrow(Constructor<?> constructor){
        if(constructor == null) return false;
        Class<?>[] throwsAnyOfTypes = constructor.getExceptionTypes();
        return throwsAnyOfTypes.length != 0;
    }
    public static boolean isConstructorVisible(Constructor<?> constructor){
        if(constructor == null) return false;
        return Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers());
    }

    public static Constructor<?> locateAutoWiredConstructor(Class<?> clazz) {
        try{
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    // Check if the @Autowired constructor is public or protected
                    return constructor;
                }
            }
        } catch (Exception e){
            return null;
        }

        return null;
    }

    /**
     * Whether a class exposes a public or protected no-args constructor or not.
     * @return An error message or null.
     */
    public static Constructor<?> locateNoArgsConstructor(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (Exception e) {
            return null;
        }
    }

}
