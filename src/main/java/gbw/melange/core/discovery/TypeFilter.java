package gbw.melange.core.discovery;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class TypeFilter {

    /**
     * Filters out all classes representing interfaces, abstract classes and annotations.
     */
    public static <T> Set<Class<? extends T>> onlyBeanables(Set<Class<? extends T>> classes){
        return classes.stream()
                .filter(clazz -> !clazz.isInterface()) // Exclude interfaces
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers())) // Exclude abstract classes
                .filter(clazz -> !clazz.isAnnotation()) // Exclude annotations
                .collect(Collectors.toSet());
    }

}
