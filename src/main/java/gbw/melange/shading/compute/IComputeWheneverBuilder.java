package gbw.melange.shading.compute;

import gbw.melange.shading.compute.service.IComputeSpecBuilder;

import java.util.function.Consumer;

public interface IComputeWheneverBuilder<T,R> extends IComputeSpecBuilder<T,R> {

    ExecutionHandle<T> build();

    void onResult(Consumer<T> function);
    void onError(Runnable function);


}
