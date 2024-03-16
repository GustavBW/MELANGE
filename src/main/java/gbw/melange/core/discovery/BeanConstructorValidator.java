package gbw.melange.core.discovery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * <p>BeanConstructorValidator class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class BeanConstructorValidator {

    private static final Logger log = LogManager.getLogger();

    /**
     * Checks if a given class can be instantiated or not in a bean-way
     *
     * @param clazz a {@link java.lang.Class} object
     * @return An error message or null;
     */
    public static String isValidClassForRegistration(Class<?> clazz) {
        return isValidClassForRegistration(clazz, null, null);
    }

    /**
     * <p>isValidClassForRegistration.</p>
     *
     * @param clazz a {@link java.lang.Class} object
     * @param noArgsOut an array of {@link java.lang.reflect.Constructor} objects
     * @param autowiredOut an array of {@link java.lang.reflect.Constructor} objects
     * @return a {@link java.lang.String} object
     */
    public static String isValidClassForRegistration(Class<?> clazz, Constructor<?>[] noArgsOut, Constructor<?>[] autowiredOut){
        Constructor<?> noArgsConstructor = locateNoArgsConstructor(clazz);
        Constructor<?> autowiredConstructor = locateAutoWiredConstructor(clazz);

        boolean noArgsConstructorValid = isConstructorVisible(noArgsConstructor) && !doesValidConstructorThrow(noArgsConstructor);
        boolean autowiredConstructorValid = isConstructorVisible(autowiredConstructor) && !doesValidConstructorThrow(autowiredConstructor);

        if(noArgsConstructorValid && noArgsOut != null){
            noArgsOut[0] = noArgsConstructor;
        }
        if(autowiredConstructorValid && autowiredOut != null){
            autowiredOut[0] = autowiredConstructor;
        }
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

    /**
     * <p>doesValidConstructorThrow.</p>
     *
     * @param constructor a {@link java.lang.reflect.Constructor} object
     * @return a boolean
     */
    public static boolean doesValidConstructorThrow(Constructor<?> constructor){
        if(constructor == null) return false;
        Class<?>[] throwsAnyOfTypes = constructor.getExceptionTypes();
        return throwsAnyOfTypes.length != 0;
    }
    /**
     * <p>isConstructorVisible.</p>
     *
     * @param constructor a {@link java.lang.reflect.Constructor} object
     * @return a boolean
     */
    public static boolean isConstructorVisible(Constructor<?> constructor){
        if(constructor == null) return false;
        return Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers());
    }

    /**
     * <p>locateAutoWiredConstructor.</p>
     *
     * @param clazz a {@link java.lang.Class} object
     * @return a {@link java.lang.reflect.Constructor} object
     */
    public static Constructor<?> locateAutoWiredConstructor(Class<?> clazz) {
        try{
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    // Check if the @Autowired constructor is public or protected
                    return constructor;
                }
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Whether a class exposes a public or protected no-args constructor or not.
     *
     * @return An error message or null.
     * @param clazz a {@link java.lang.Class} object
     */
    public static Constructor<?> locateNoArgsConstructor(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (Exception e) {
            return null;
        }
    }

}
