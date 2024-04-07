package gbw.melange.shading.compute.services;

import gbw.melange.shading.compute.requests.IComputeLaterBuilder;
import gbw.melange.shading.compute.requests.IComputeNowBuilder;
import gbw.melange.shading.compute.requests.IComputeWheneverBuilder;

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
     * Add the request to the back of the execution queue. Define a callback to what to do when the request has been resolved.
     * @param <T> The end result type
     * @param <R> The type of the result of the chosen capture method
     */
    <T,R> IComputeWheneverBuilder<T,R> whenever();

    /**
     * Prepare to schedule a synchronous execution, but don't add it to the execution queue just yet.
     * Schedule and block when the result is requested. No callback needed.
     * @param <T> The end result type
     * @param <R> The type of the result of the chosen capture method
     */
    <T,R> IComputeLaterBuilder<T,R> later();

}
