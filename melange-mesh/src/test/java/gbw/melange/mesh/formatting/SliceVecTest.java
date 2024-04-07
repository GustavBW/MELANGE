package gbw.melange.mesh.formatting;

import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.mesh.constants.SliceProviders;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SliceVecTest {
    private static final float[] sourceLen10 = {1f,2f,3f,4f,5f,6f,7f,8f,9f,10f};
    @Test
    void testDirectModification(){
        final float[] copy = {1f,2f,3f,4f,5f,6f,7f,8f,9f,10f};
        ISliceVec2 vec2 = SliceProviders.createVec2.create(copy, 0,2);
        vec2.x(69);
        vec2.y(610);

        assertEquals(69, copy[0], "Any modifications should be present in the source array.");
        assertEquals(610, copy[1], "Any modifications should be present in the source array.");
    }

}
