package gbw.melange.mesh.constants;

import gbw.melange.common.mesh.formatting.providers.SliceProvider;
import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec4;
import gbw.melange.mesh.formatting.FloatSlice;
import gbw.melange.mesh.formatting.slicing.SliceVec2;
import gbw.melange.mesh.formatting.slicing.SliceVec3;
import gbw.melange.mesh.formatting.slicing.SliceVec4;

public interface SliceProviders {
    // ======== PROVIDERS ===========
    SliceProvider<IFloatSlice> create = FloatSlice::new;
    SliceProvider<ISliceVec2> createVec2 = (source, index, width) -> {
        if(width < 2){
            throw new IllegalArgumentException("A 2 component vector slice provider was invoked with a " +
                    "slice width less than 2.\n This will most certainly cause problems. " +
                    "Check any referenced providers for a mis-match (e.g. IFloatSlice::createVec2 was referenced instead of IFloatSlice::create");
        }
        return new SliceVec2(source, index);
    };
    SliceProvider<ISliceVec3> createVec3 = (source, index, width) -> {
        if(width < 3){
            throw new IllegalArgumentException("A 3 component vector slice provider was invoked with a " +
                    "slice width less than 3.\n This will most certainly cause problems. " +
                    "Check any referenced providers for a mis-match (e.g. IFloatSlice::createVec3 was referenced instead of IFloatSlice::createVec2");
        }
        return new SliceVec3(source, index);
    };
    SliceProvider<ISliceVec4> createVec4 = (source, index, width) -> {
        if(width < 4){
            throw new IllegalArgumentException("A 4 component vector slice provider was invoked with a " +
                    "slice width less than 4.\n This will most certainly cause problems. " +
                    "Check any referenced providers for a mis-match (e.g. IFloatSlice::createVec4 was referenced instead of IFloatSlice::createVec3");
        }
        return new SliceVec4(source, index);
    };
}
