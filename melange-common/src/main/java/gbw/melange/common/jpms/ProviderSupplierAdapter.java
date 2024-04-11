package gbw.melange.common.jpms;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public class ProviderSupplierAdapter {
    public static <T> ServiceLoader.Provider<T> from(Supplier<T> supplier, Class<T> classRef){
        return new ServiceLoader.Provider<>(){
            @Override
            public Class<T> type(){
                return classRef;
            }
            @Override
            public T get(){
                return supplier.get();
            }
        };
    }
}
