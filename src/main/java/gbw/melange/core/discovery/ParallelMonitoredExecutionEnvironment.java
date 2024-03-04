package gbw.melange.core.discovery;

import gbw.melange.common.hooks.OnInit;

import java.util.List;

/**
 * OnInit hook implementations can cause Exceptions and may take a lot of time to execute if they blockingly fetch external data.
 */
public class ParallelMonitoredExecutionEnvironment {

    public static void handleThis(List<OnInit> onInit){
        onInit.forEach(e -> {
            try {
                e.onInit();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
