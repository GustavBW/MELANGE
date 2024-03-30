package gbw.melange.shading.compute.service;

import gbw.melange.shading.compute.IComputeLaterBuilder;
import gbw.melange.shading.compute.IComputeNowBuilder;
import gbw.melange.shading.compute.IComputeWheneverBuilder;

/**
 * Schedule jobs to be run on the GPU
 */
public interface GPUCompute {

    /**
     * Immediately schedule and execute blockingly.
     * @param <T> The end result type
     * @param <R> The type of the result of the chosen capture method
     */
    <T,R> IComputeNowBuilder<T,R> now();

    /**
     * Schedule execution, but only execute when there's available time.
     * @param <T> The end result type
     * @param <R> The type of the result of the chosen capture method
     */
    <T,R> IComputeWheneverBuilder<T,R> whenever();

    /**
     * Prepare to schedule a synchronous execution, but only compute when the result is requested.
     * @param <T> The end result type
     * @param <R> The type of the result of the chosen capture method
     */
    <T,R> IComputeLaterBuilder<T,R> later();

}
