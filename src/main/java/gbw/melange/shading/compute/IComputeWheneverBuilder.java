package gbw.melange.shading.compute;

public interface IComputeWheneverBuilder<T> extends IComputeSpecBuilder<T> {

    ExecutionHandle<T> request();


}
