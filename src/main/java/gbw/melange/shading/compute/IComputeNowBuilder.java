package gbw.melange.shading.compute;

public interface IComputeNowBuilder<T> extends IComputeSpecBuilder<T> {

    T exec();

}
