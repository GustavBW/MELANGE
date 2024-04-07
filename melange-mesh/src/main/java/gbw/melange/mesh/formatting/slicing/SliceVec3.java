package gbw.melange.mesh.formatting.slicing;

import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec3;

/**
 * @author GustavBW
 */
public class SliceVec3 extends SliceVec2 implements IFloatSlice, ISliceVec3 {

    public SliceVec3(float[] source, int indexFirstComp) {
        this(source, indexFirstComp, 3);
    }
    public SliceVec3(float[] source, int indexFirstComp, int width) {
        super(source, indexFirstComp, width);
        if(width < 3){
            throw new IllegalArgumentException("A SliceVec3 was created with an underlying FloatSlice width of: " + width);
        }
    }

    @Override
    public SliceVec3 copy(){
        return new SliceVec3(source, indexFirstComp());
    }

    @Override
    public float z() {
        return super.get(2);
    }

    @Override
    public void z(float value) {
        super.set(2, value);
    }

}
