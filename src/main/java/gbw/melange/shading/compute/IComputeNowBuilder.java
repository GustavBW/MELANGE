package gbw.melange.shading.compute;

import gbw.melange.shading.compute.service.IComputeSpecBuilder;

public interface IComputeNowBuilder<T,R> extends IComputeSpecBuilder<T,R> {

    T exec();

}
