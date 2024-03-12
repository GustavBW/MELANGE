package gbw.melange.core;

import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.hooks.OnInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * OnInit hook implementations can cause Exceptions and may take a lot of time to execute if they blockingly fetch external data.
 */
public class ParallelMonitoredExecutionEnvironment {

    private static final Logger log = LoggerFactory.getLogger(ParallelMonitoredExecutionEnvironment.class);
    private static ParallelMonitoredExecutionEnvironment instance;
    private static final ExecutorService executor = Executors.newFixedThreadPool(16);
    private final MelangeApplication<?> appInstance;
    private ParallelMonitoredExecutionEnvironment(MelangeApplication<?> appInstance){
        this.appInstance = appInstance;
    }
    private void runOnMain(Runnable any){
        appInstance.handleOnMain(any);
    }

    static <T> void setInstance(MelangeApplication<T> appInstance){
        if(appInstance == null) throw new RuntimeException("Hell no. MelangeApplication instance provided to PMEE is null");
        instance = new ParallelMonitoredExecutionEnvironment(appInstance);
    }

    public static void handleThis(List<OnInit<?>> onInits){
        //VolatileElements are handled separately, and if reflections pick any of these up, its a mistake.
        List<OnInit<?>> filtered = new ArrayList<>();
        for(OnInit<?> onInit : onInits){
            if(!(onInit instanceof ILoadingElement<?>)){
                filtered.add(onInit);
            }
        }

        filtered.forEach(e ->
            executor.submit(() ->
                {
                    try {
                        e.onInit();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            ));
    }
    public static <T> void offloadLoadingElement(ILoadingElement<T> element, Runnable moveToStable, Runnable moveToError){
        executor.submit(() -> instance.handleLoadingElement(element, moveToStable, moveToError));
    }
    private <T> void handleLoadingElement(ILoadingElement<T> element, Runnable moveToStable, Runnable moveToError){
        try{
            element.invokeProvider();
            moveToStable.run();
        }catch(Exception handled){
            log.warn("LoadingElement "+element+" content provider failed! " + handled.getMessage());
            moveToError.run();
        }
    }
    /**
     * Awaits the predicate in 100ms sleep intervals, then submits whenCompletedDo to be run on the main thread.
     */
    public static <T> void offloadAny(T object, Predicate<T> completeWhenTrue, Consumer<T> whenCompleteDo){
        executor.submit(() -> instance.handle0(object, completeWhenTrue, whenCompleteDo));
    }
    private <T> void handle0(T object, Predicate<T> completeWhenTrue, Consumer<T> whenCompleteDo){
        boolean complete = false;
        while(!complete){
            complete = completeWhenTrue.test(object);
            try{
                Thread.sleep(100);
            }catch (Exception ignored){}
        }
        //Since we don't know what this task might be, its probably best to run it on the main thread.
        instance.runOnMain(() -> whenCompleteDo.accept(object));
    }

    static void shutdown(){
        executor.shutdown();
    }

}
