package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EVertexAttributeTest {

    @Test
    void convertListToArray() {
        List<EVertexAttribute> attributes = Arrays.asList(EVertexAttribute.POSITION, EVertexAttribute.COLOR_UNPACKED);
        VertexAttribute[] converted = EVertexAttribute.convert(attributes);
        assertEquals(attributes.size(), converted.length, "The size of the output array should match the input list.");
        for (int i = 0; i < attributes.size(); i++) {
            EVertexAttribute original = attributes.get(i);
            VertexAttribute attribute = converted[i];
            assertTrue(original.equals(attribute), "Converted attribute should match the original.");
        }
    }
    @Test
    void convertSetToArray() {
        HashSet<EVertexAttribute> attributeSet = new HashSet<>(Arrays.asList(EVertexAttribute.POSITION, EVertexAttribute.NORMAL));
        VertexAttribute[] converted = EVertexAttribute.convert(attributeSet);
        assertEquals(attributeSet.size(), converted.length, "The size of the output array should match the input set.");
        // Additional checks can be similar to the list conversion test
    }

    @Test
    void convertVertexAttributesToList() {
        VertexAttribute[] vertexAttributesArray = {
                new VertexAttribute(EVertexAttribute.POSITION.usage(), EVertexAttribute.POSITION.componentCount(), EVertexAttribute.POSITION.alias()),
                new VertexAttribute(EVertexAttribute.COLOR_UNPACKED.usage(), EVertexAttribute.COLOR_UNPACKED.componentCount(), EVertexAttribute.COLOR_UNPACKED.alias())
        };
        VertexAttributes vertexAttributes = new VertexAttributes(vertexAttributesArray);
        List<EVertexAttribute> convertedList = EVertexAttribute.convert(vertexAttributes);
        assertEquals(vertexAttributes.size(), convertedList.size(), "The size of the converted list should match the VertexAttributes.");
        // Additional checks for correctness of conversion
    }

    @Test
    void testEquals() {
        VertexAttribute va = new VertexAttribute(EVertexAttribute.POSITION.usage(), EVertexAttribute.POSITION.componentCount(), EVertexAttribute.POSITION.alias());
        assertTrue(EVertexAttribute.POSITION.equals(va), "EVertexAttribute should equal the corresponding VertexAttribute.");
    }

    @Test
    void asVA() {
        VertexAttribute va = EVertexAttribute.POSITION.asVA();
        assertNotNull(va, "The method should return a non-null VertexAttribute.");
        assertEquals(EVertexAttribute.POSITION.usage(), va.usage, "Usage should match.");
        assertEquals(EVertexAttribute.POSITION.componentCount(), va.numComponents, "Number of components should match.");
        assertEquals(EVertexAttribute.POSITION.alias(), va.alias, "Alias should match.");
    }

    @Test
    void compare() {
        VertexAttribute va = EVertexAttribute.POSITION.asVA();
        assertTrue(EVertexAttribute.compare(va, EVertexAttribute.POSITION), "Compare method should return true for equal attributes.");
    }
}