package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnownAttributesTest {

    @Test
    void convertListToArray() {
        List<IVertAttr> attributes = Arrays.asList(KnownAttributes.POSITION, KnownAttributes.COLOR_UNPACKED);
        VertexAttribute[] converted = KnownAttributes.convert(attributes);
        assertEquals(attributes.size(), converted.length, "The size of the output array should match the input list.");
        for (int i = 0; i < attributes.size(); i++) {
            IVertAttr original = attributes.get(i);
            VertexAttribute attribute = converted[i];
            assertTrue(original.equals(attribute), "Converted attribute should match the original.");
        }
    }
    @Test
    void convertSetToArray() {
        HashSet<IVertAttr> attributeSet = new HashSet<>(Arrays.asList(KnownAttributes.POSITION, KnownAttributes.NORMAL));
        VertexAttribute[] converted = KnownAttributes.convert(attributeSet);
        assertEquals(attributeSet.size(), converted.length, "The size of the output array should match the input set.");
        // Additional checks can be similar to the list conversion test
    }

    @Test
    void convertVertexAttributesToList() {
        VertexAttribute[] vertexAttributesArray = {
                new VertexAttribute(KnownAttributes.POSITION.usage(), KnownAttributes.POSITION.compCount(), KnownAttributes.POSITION.alias()),
                new VertexAttribute(KnownAttributes.COLOR_UNPACKED.usage(), KnownAttributes.COLOR_UNPACKED.compCount(), KnownAttributes.COLOR_UNPACKED.alias())
        };
        VertexAttributes vertexAttributes = new VertexAttributes(vertexAttributesArray);
        List<IVertAttr> convertedList = KnownAttributes.convert(vertexAttributes);
        assertEquals(vertexAttributes.size(), convertedList.size(), "The size of the converted list should match the VertexAttributes.");
        // Additional checks for correctness of conversion
    }

    @Test
    void testEquals() {
        VertexAttribute va = new VertexAttribute(KnownAttributes.POSITION.usage(), KnownAttributes.POSITION.compCount(), KnownAttributes.POSITION.alias());
        assertTrue(KnownAttributes.POSITION.equals(va), "KnownAttributes should equal the corresponding IVertAttr.");
    }

    @Test
    void asVA() {
        VertexAttribute va = KnownAttributes.POSITION.asVA();
        assertNotNull(va, "The method should return a non-null IVertAttr.");
        assertEquals(KnownAttributes.POSITION.usage(), va.usage, "Usage should match.");
        assertEquals(KnownAttributes.POSITION.compCount(), va.numComponents, "Number of components should match.");
        assertEquals(KnownAttributes.POSITION.alias(), va.alias, "Alias should match.");
    }

    @Test
    void compare() {
        VertexAttribute va = KnownAttributes.POSITION.asVA();
        assertTrue(KnownAttributes.compare(va, KnownAttributes.POSITION), "Compare method should return true for equal attributes.");
    }
}