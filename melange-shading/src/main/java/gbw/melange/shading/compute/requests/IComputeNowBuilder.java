package gbw.melange.shading.compute.requests;

public interface IComputeNowBuilder<T,R> extends IComputeReqBuilder<T,R> {

    ExecHandleNow<T> build();

}
