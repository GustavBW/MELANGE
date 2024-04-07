package gbw.melange.core;

import gbw.melange.common.elementary.types.ILoadingElement;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.core.app.MelangeApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * OnInit hook implementations can cause Exceptions and may take a lot of time to execute if they blockingly fetch external data.
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public class ParallelMonitoredExecutionEnvironment {

    private static final Logger log = LogManager.getLogger();
    private static ParallelMonitoredExecutionEnvironment instance;
    private static final ExecutorService executor = Executors.newFixedThreadPool(16);
    private final ConcurrentLinkedQueue<Runnable> runOnMainThread;
    private ParallelMonitoredExecutionEnvironment(ConcurrentLinkedQueue<Runnable> runOnMainThread){
        this.runOnMainThread = runOnMainThread;
    }
    private void runOnMain(Runnable any){
        runOnMainThread.add(any);
    }

    public static <T> void setMainThreadQueue(ConcurrentLinkedQueue<Runnable> runOnMainThread){
        if(runOnMainThread == null) throw new RuntimeException("Hell no. Threadsafe Queue instance provided to PMEE is null");
        instance = new ParallelMonitoredExecutionEnvironment(runOnMainThread);
    }

    /**
     * <p>handleThis.</p>
     *
     * @param onInits a {@link java.util.List} object
     */
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
            log.warn("LoadingElement "+element+" content provider failed!");
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

    public static void shutdown(){
        executor.shutdown();
    }

}
