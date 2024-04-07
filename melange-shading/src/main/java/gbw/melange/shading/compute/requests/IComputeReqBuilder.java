package gbw.melange.shading.compute.requests;

import gbw.melange.shading.compute.capture.ICaptureMethod;

import java.util.function.Function;

/**
 *
 * @param <T> The end result output format
 * @param <R> The format the data will be in when captured from the GPU
 */
public interface IComputeReqBuilder<T, R> {

    /**
     * How to capture the result from the GPU
     * @param captureMethod
     */
    void setCaptureMethod(ICaptureMethod<R> captureMethod);

    /**
     * Any further action to take to format the result of the request before making it available.
     * (NB: Technically a generic serializer can be used, but would be ill-advised given that the type of the result of the capture method is known)
     */
    void setFormatter(Function<R, T> formatter);

    void addInput(int[] intBuffer);
    void addInput(float[] floatBuffer);
    void addInput(double[] doubleBuffer);


}
