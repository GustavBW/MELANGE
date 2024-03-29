package gbw.melange.shading.compute;

import gbw.melange.shading.compute.capture.ICaptureMethod;

import java.util.function.Function;

/**
 *
 * @param <T> The end result output format
 * @param <R> The format the data will be in when captured from the GPU
 */
public interface IComputeSpecBuilder<T, R> {

    void setCaptureMethod(ICaptureMethod<R> captureMethod);
    void setFormatter(Function<R, T> formatter);

    void addInput(int[] intBuffer);
    void addInput(float[] floatBuffer);
    void addInput(double[] doubleBuffer);

}
