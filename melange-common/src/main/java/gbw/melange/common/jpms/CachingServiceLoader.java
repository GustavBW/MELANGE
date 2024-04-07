package gbw.melange.common.jpms;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class CachingServiceLoader {

    private static final Map<Class<?>, ServiceLoader.Provider<?>> cachedProviders = new ConcurrentHashMap<>();

    public static <T> T get(Class<T> clazz){
        ServiceLoader<T> loader = ServiceLoader.load(clazz);
    }

}
