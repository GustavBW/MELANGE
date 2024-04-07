package gbw.melange.shading.compute.requests;

public interface ExecHandleNow<T> {

    /**
     * Schedule the request for execution. Ignored if already scheduled.
     */
    T exec();
}
