package gbw.melange.shading.compute.capture;

public interface ICaptureMethod<T> {
    void setup(); // Setup resources or configurations needed.
    void execute(); // Execute the capture.
    T getResult(); // Retrieve the results.
}