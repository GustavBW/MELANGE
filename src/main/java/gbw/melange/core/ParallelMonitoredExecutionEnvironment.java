package gbw.melange.core;

import gbw.melange.common.hooks.OnInit;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * OnInit hook implementations can cause Exceptions and may take a lot of time to execute if they blockingly fetch external data.
 */
public class ParallelMonitoredExecutionEnvironment {

    private static ParallelMonitoredExecutionEnvironment instance;
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

    public static void handleThis(List<OnInit> onInit){
        //If is IElement, throw them into Loading state and move them to Loading render queue in their space
        //When it resolves, go stable or dedicated error.

        onInit.forEach(e -> {
            try {
                e.onInit();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static <T> void handle(T object, Predicate<T> completeWhenTrue, Consumer<T> whenCompleteDo){
        Thread handleThread = new Thread(() -> handle0(object, completeWhenTrue, whenCompleteDo));
        handleThread.start();
    }
    private static <T> void handle0(T object, Predicate<T> completeWhenTrue, Consumer<T> whenCompleteDo){
        boolean complete = false;
        while(!complete){
            complete = completeWhenTrue.test(object);
            try{
                Thread.sleep(100);
            }catch (Exception ignored){}
        }
        instance.runOnMain(() -> whenCompleteDo.accept(object));
    }

}
