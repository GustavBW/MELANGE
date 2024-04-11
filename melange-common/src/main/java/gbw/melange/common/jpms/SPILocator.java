package gbw.melange.common.jpms;

import gbw.melange.common.errors.ServiceLoadingFailure;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.ServiceLoader.Provider;

/**
 * Revised ServiceLoader utility with multi-stage caching and bean-like behaviour.
 * @author GustavBW
 */
public class SPILocator {
    /**
     * Instantiated beans. Much like an ApplicationContext without @AutoWired
     */
    private static final Map<Class<?>, List<?>> serviceInstancesMap = new ConcurrentHashMap<>();
    /**
     * Resolved SPI declarations.
     */
    private static final Map<SPI<?>, List<Provider<?>>> spiProvidersMap = new ConcurrentHashMap<>();
    /**
     * Actual ServiceLoaders for different interfaces. Not resolved. Raw.
     */
    private static final Map<Class<?>, ServiceLoader<?>> servicesLoaderMap = new ConcurrentHashMap<>();

    public static <T> T loadService(SPI<T> spi){
        System.out.println("SPIL resoliving spi: " + spi);
        List<T> beans = loadServices(spi);
        System.out.println("SPIL loaded services: " + beans);
        if(beans == null || beans.isEmpty()){
            //Getting here with an unresolvable SPI definition should
            //be impossible without already having thrown a ServiceLoadingFailure (runtime exception)
            //but just in case:
            throw new ServiceLoadingFailure("Unable to resolve " + spi + ". No provided implementations where found for the original interface, any fallbacks and the assured supplier failed too.");
        }
        return beans.get(0);
    }

    /**
     * Loads any service providers of said type, caches and returns the instances provided.<br>
     * May throw a ClassCastException on service configuration error.<br>
     * @param spi spi definition. Can be obtained using {@link SPI#of(Class, Supplier, Class[])}
     * @return instances of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadServices(SPI<T> spi) {
        return (List<T>) serviceInstancesMap.computeIfAbsent(spi.getServiceInterface(), k -> getProvidersOf(spi)
                .stream()
                .map(Provider::get)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new))
        );
    }

    /**
     * Loads any service providers of said type, caches and returns the providers.<br>
     * Duly note that invoking the providers returns a new instance. If you want access to the services already instantiated,
     * use {@link SPILocator#loadService(SPI)} or {@link SPILocator#loadServices(SPI)}<br>
     * May throw a ClassCastException on service configuration error.<br>
     * @param spi spi definition
     * @return providers of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Provider<T>> getProvidersOf(SPI<T> spi){
        List<Provider<T>> initialAttempt = (List<Provider<T>>) (List<?>) spiProvidersMap.computeIfAbsent(
                spi, k -> getLoader(spi.getServiceInterface())
                        .stream()
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new))
        );
        if(!initialAttempt.isEmpty()){
            System.out.println("SPIL wasn't empty: " + initialAttempt);
            return initialAttempt;
        }
        System.out.println("SPIL checking fallbacks");
        while(spi.hasNextFallback()){
            Class<T> fallback = spi.getNextFallback();
            List<Provider<T>> fallbackAttempt = (List<Provider<T>>) (List<?>) spiProvidersMap.computeIfAbsent(
                    spi, k -> getLoader(fallback)
                            .stream()
                            .collect(Collectors.toCollection(CopyOnWriteArrayList::new))
            );
            if(!fallbackAttempt.isEmpty()){
                return fallbackAttempt;
            }
        }
        System.out.println("SPIL mapping supplier to provider");
        ServiceLoader.Provider<T> fromAssuredSupplier = ProviderSupplierAdapter.from(spi::getFromSupplier, spi.getServiceInterface());
        return (List<Provider<T>>) (List<?>) spiProvidersMap.put(spi, List.of(fromAssuredSupplier));
    }

    private static ServiceLoader<?> getLoader(Class<?> clazz){
        return servicesLoaderMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz));
    }

}
