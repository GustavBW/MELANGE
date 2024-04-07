package gbw.melange.shading.compute.requests;

import java.util.function.Consumer;

public interface IComputeWheneverBuilder<T,R> extends IComputeReqBuilder<T,R> {

    ExecHandleWhenever build();

    void onResult(Consumer<T> function);
    void onError(Runnable function);


}
