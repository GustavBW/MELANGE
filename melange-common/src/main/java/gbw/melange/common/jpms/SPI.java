package gbw.melange.common.jpms;

import gbw.melange.common.errors.Errors;
import gbw.melange.common.errors.Error;
import gbw.melange.common.errors.ServiceLoadingFailure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Service Provider Interface wrapper.
 * Or, the wrapper for an interface for any Service Provider.
 * Any provided service aimed to be distributed through ServiceLoading should extend this interface to allow for dynamic fallback
 * handling and introspection when used with the {@link SPILocator}. <br/>
 * In general, the {@link SPILocator} will attempt to retrieve the original first, then all provided fallbacks (if any) and lastly
 * the provided assured supplier (if any). If nothing at all can be found, it will throw a {@link ServiceLoadingFailure} instead of returning null, because we don't do that around here.
 * @param <T> The shared type between the original Service Interface and all fallbacks. Used to retain type definition for the SPILocator.
 */
public abstract class SPI<T> {
    public static final Class<?> NONE = Void.class;

    private static final Logger log = LogManager.getLogger();
    private final Class<T> spi;
    private final Class<T>[] fallbacks;
    private final boolean hasFallbacks, hasAssuredSupplier;
    private final Supplier<T> assuredSupplier;
    private int fallbackIterationIndex = 0;

    @SafeVarargs
    public static <T> SPI<T> of(Class<T> original, Class<T>... fallbacks){
        return new SPI<>(original, null, fallbacks) {};
    }
    @SafeVarargs
    public static <T> SPI<T> of(Class<T> original, Supplier<T> assuredSupplier, Class<T>... fallbacks){
        return new SPI<>(original, assuredSupplier, fallbacks) {};
    }
    @SafeVarargs
    private SPI(Class<T> clazz, Supplier<T> assuredSupplier, Class<T>... fallbacks){
        this.spi = clazz;
        this.fallbacks = fallbacks;
        this.assuredSupplier = assuredSupplier;
        this.hasFallbacks = fallbacks != null && fallbacks.length > 0 && fallbacks[0] != null;
        //The constructor stuff is just checking if the service implementation has a no-args constructor.
        this.hasAssuredSupplier = assuredSupplier != null && Errors.deescalate(clazz::getConstructor) == Error.NONE;
    }

    public Class<T> getServiceInterface(){
        return spi;
    }
    public boolean hasNextFallback(){
        if(hasFallbacks){
            return fallbackIterationIndex > fallbacks.length;
        }
        return false;
    }
    public boolean hasAssuredSupplier(){
        return hasAssuredSupplier;
    }

    //General note about the following stuff in this class:
    //This class is behaviourally tightly coupled with the SPILocator,
    //and for all intents and purposes, they should be seen as "one".
    //For that reason, the following methods are ONLY VISIBLE TO THE SPILocator and should remain that way.
    //Of course this class can be extended and modified to suit anyone's needs.

    /* package-private, stateful iterator-like and so only to be used by the SPILocator */
    Class<T> getNextFallback(){
        if(!hasFallbacks || fallbackIterationIndex > fallbacks.length){
            log.warn("No fallbacks where defined for " + this.spi + " yet a fallback was required as no implementations where found initially.");
            return (Class<T>) NONE;
        }
        Class<T> nextUp = fallbacks[fallbackIterationIndex++];
        log.info("Falling back on " + spi + " to + " + nextUp + " as the requested service was not found.");
        return nextUp;
    }
    /* package-private, this method is the last ditch effort in the process of ServiceLoading and is thus allowed to throw if invoked and an assured supplier isn't present. */
    T getFromSupplier(){
        if(hasAssuredSupplier){
            log.warn("The assured supplier should only be used as a last ditch effort, and was for " + this + ".");
            return assuredSupplier.get();
        }
        throw new ServiceLoadingFailure("All provided references, original interface, fallback interfaces (if any) and supplier (if any) failed to retrieve the requested service.");
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(spi) + Arrays.hashCode(fallbacks);
    }

    @Override
    public String toString(){
        return "SPI{originalClass: " + spi + ", fallbacks: " + fallbacks + ", assuredSupplier: " + assuredSupplier + "}";
    }

}
