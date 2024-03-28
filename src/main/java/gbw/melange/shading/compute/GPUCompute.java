package gbw.melange.shading.compute;

public interface GPUCompute {

    /**
     * Await execution / synchronous
     */
    <T> IComputeNowBuilder<T> now();

    /**
     * Schedule execution, but only execute when there's available time.
     */
    <T> IComputeSpecBuilder<T> whenever();

    /**
     * Prepare to schedule a synchronous execution, but only compute when the result is requested.
     */
    <T> IComputeLaterBuilder<T> later();

}
