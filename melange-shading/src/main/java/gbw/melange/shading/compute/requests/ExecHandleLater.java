package gbw.melange.shading.compute.requests;

public interface ExecHandleLater<T> {
    void schedule();
    boolean isScheduled();
    T exec();
}
