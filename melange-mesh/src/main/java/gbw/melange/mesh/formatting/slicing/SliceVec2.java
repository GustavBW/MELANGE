package gbw.melange.mesh.formatting.slicing;


import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.mesh.formatting.FloatSlice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author GustavBW
 */
public class SliceVec2 extends FloatSlice implements IFloatSlice, ISliceVec2 {

    private static final Logger log = LogManager.getLogger();

    public SliceVec2(float[] source, int indexFirstComp) {
        this(source, indexFirstComp, 2);
    }
    public SliceVec2(float[] source, int indexFirstComp, int width) {
        super(source, indexFirstComp, width);
        if(width < 2){
            throw new IllegalArgumentException("A SliceVec2 was created with an underlying FloatSlice width of: " + width);
        }
    }

    @Override
    public SliceVec2 copy(){
        return new SliceVec2(source, indexFirstComp());
    }

    @Override
    public float x() {
        return super.unsafeGet(0);
    }

    @Override
    public float y() {
        return super.unsafeGet(1);
    }

    @Override
    public void x(float value) {
        super.unsafeSet(0, value);
    }

    @Override
    public void y(float value) {
        super.unsafeSet(1, value);
    }

}
