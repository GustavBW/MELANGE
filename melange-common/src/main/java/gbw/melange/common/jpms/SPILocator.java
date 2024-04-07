package gbw.melange.common.jpms;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.ServiceLoader.Provider;

/**
 * Revised ServiceLoader utility.
 * @author GustavBW
 */
public class SPILocator {

    private static final Map<Class<?>, List<?>> serviceInstancesMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<Provider<?>>> servicesProvidersMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, ServiceLoader<?>> servicesLoaderMap = new ConcurrentHashMap<>();

    public static <T> T getBean(Class<T> clazz){
        return getBeans(clazz).get(0);
    }

    /**
     * Loads any service providers of said type, caches and returns the instances provided.<br>
     * May throw a ClassCastException on service configuration error.<br>
     * @param clazz Type implementations to look for<br>
     * @return instances of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getBeans(Class<T> clazz) {
        return (List<T>) serviceInstancesMap.computeIfAbsent(clazz, k -> getProvidersOf(clazz)
                .stream()
                .map(Provider::get)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new))
        );
    }

    /**
     * Loads any service providers of said type, caches and returns the providers.<br>
     * Duly note that invoking the providers returns a new instance.<br>
     * May throw a ClassCastException on service configuration error.<br>
     * @param clazz Type of provider to look for<br>
     * @return providers of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Provider<T>> getProvidersOf(Class<T> clazz){
        return (List<Provider<T>>) (List<?>) servicesProvidersMap.computeIfAbsent(
                clazz, k -> getLoader(clazz)
                        .stream()
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new)));
    }

    /**
     * Retrieves a ServiceLoader of said type.<br>
     * @param clazz Type of service to load<br>
     * @return a ServiceLoader for that type
     */
    @SuppressWarnings("unchecked")
    public static <T> ServiceLoader<T> getLoaderFor(Class<T> clazz){
        return (ServiceLoader<T>) getLoader(clazz);
    }

    private static ServiceLoader<?> getLoader(Class<?> clazz){
        return servicesLoaderMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz));
    }

}
