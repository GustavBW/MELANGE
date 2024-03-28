package gbw.melange.shading.compute;

public interface ExecutionHandle<T> {

    T get();
    boolean hasCompleted();
    boolean isScheduled();

}
