package gbw.melange.shading.compute;

public interface IComputeNowBuilder<T,R> extends IComputeSpecBuilder<T,R> {

    T exec();

}
