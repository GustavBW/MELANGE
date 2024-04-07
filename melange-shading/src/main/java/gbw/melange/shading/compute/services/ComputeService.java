package gbw.melange.shading.compute.services;

import gbw.melange.shading.compute.requests.IComputeLaterBuilder;
import gbw.melange.shading.compute.requests.IComputeNowBuilder;
import gbw.melange.shading.compute.requests.IComputeWheneverBuilder;

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
