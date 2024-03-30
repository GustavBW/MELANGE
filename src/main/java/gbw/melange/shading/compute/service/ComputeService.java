package gbw.melange.shading.compute.service;

import gbw.melange.shading.compute.IComputeLaterBuilder;
import gbw.melange.shading.compute.IComputeNowBuilder;
import gbw.melange.shading.compute.IComputeWheneverBuilder;

public class ComputeService implements GPUCompute {


    @Override
    public <T,R> IComputeNowBuilder<T,R> now() {
        return null;
    }

    @Override
    public <T,R> IComputeWheneverBuilder<T,R> whenever() {
        return null;
    }

    @Override
    public <T,R> IComputeLaterBuilder<T,R> later() {
        return null;
    }
}
