package gbw.melange.shading.compute;

public class ComputeService implements GPUCompute {


    @Override
    public <T> IComputeNowBuilder<T> now() {
        return null;
    }

    @Override
    public <T> IComputeSpecBuilder<T> whenever() {
        return null;
    }

    @Override
    public <T> IComputeLaterBuilder<T> later() {
        return null;
    }
}
