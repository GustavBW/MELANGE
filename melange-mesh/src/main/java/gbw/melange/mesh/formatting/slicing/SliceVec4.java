package gbw.melange.mesh.formatting.slicing;

import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec4;

/**
 * @author GustavBW
 */
public class SliceVec4 extends SliceVec3 implements IFloatSlice, ISliceVec4 {

    public SliceVec4(float[] source, int indexFirstComp) {
        this(source, indexFirstComp, 4);
    }
    public SliceVec4(float[] source, int indexFirstComp, int width) {
        super(source, indexFirstComp, width);
        if(width < 4){
            throw new IllegalArgumentException("A SliceVec4 was created with an underlying FloatSlice width of: " + width);
        }
    }

    @Override
    public SliceVec4 copy(){
        return new SliceVec4(source, indexFirstComp());
    }

    @Override
    public float w() {
        return super.unsafeGet(3);
    }

    @Override
    public void w(float value) {
        super.unsafeSet(3, value);
    }


}
