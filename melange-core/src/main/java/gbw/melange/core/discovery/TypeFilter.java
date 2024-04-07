package gbw.melange.core.discovery;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>TypeFilter class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class TypeFilter {

    /**
     * Filters out all classes representing interfaces, abstract classes and annotations.
     *
     * @param classes a {@link java.util.Set} object
     * @param <T> a T class
     * @return a {@link java.util.Set} object
     */
    public static <T> Set<Class<? extends T>> retainInstantiable(Set<Class<? extends T>> classes){
        return classes.stream()
                .filter(clazz -> !clazz.isInterface()) // Exclude interfaces
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers())) // Exclude abstract classes
                .filter(clazz -> !clazz.isAnnotation()) // Exclude annotations
                .collect(Collectors.toSet());
    }

}
