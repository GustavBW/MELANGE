package gbw.melange.mesh.formatting;

import gbw.melange.mesh.formatting.slicing.IFloatSlice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FloatSliceTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    private static final float[] sourceLen10 = {1f,2f,3f,4f,5f,6f,7f,8f,9f,10f};

    @Test
    void testValidConstruction() {
        Assertions.assertDoesNotThrow(() -> new FloatSlice(sourceLen10, 1, 3));
    }

    @Test
    void testGetValidIndex() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, 3);
        Assertions.assertEquals(1, slice.get(0));
        Assertions.assertEquals(2, slice.get(1));
        Assertions.assertEquals(3, slice.get(2));
    }

    @Test
    void testGetInvalidIndex() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 1, 3);
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> slice.get(3));
    }

    @Test
    void testIteratorCompleteTraversal() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, 3);
        Iterator<Float> it = slice.iterator();

        assertTrue(it.hasNext());
        assertEquals(1f, it.next());
        assertTrue(it.hasNext());
        assertEquals(2f, it.next());
        assertTrue(it.hasNext());
        assertEquals(3f, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testIteratorNext() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 1, 2); // Slice includes {2f, 3f}
        Iterator<Float> iterator = slice.iterator();

        assertEquals(Float.valueOf(2f), iterator.next());
        assertEquals(Float.valueOf(3f), iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next); // Beyond end
    }

    @Test
    void testIteratorResetBehavior() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, 2); // Slice includes {1f, 2f}
        Iterator<Float> iterator = slice.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());

        // Reset and test again
        slice.iterator(); // Implicitly resets
        assertTrue(iterator.hasNext());
        assertEquals(Float.valueOf(1f), iterator.next(), "After reset, first element should be accessible again.");
    }

    @Test
    void testIteratorHasNext() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, 3);
        Iterator<Float> iterator = slice.iterator();

        assertTrue(iterator.hasNext()); // Expect true initially
        iterator.next(); // Move to the next element
        assertTrue(iterator.hasNext()); // Still has elements
        iterator.next();
        iterator.next(); // Last element
        assertFalse(iterator.hasNext()); // No more elements
    }

    @Test
    void testForEachConsumerBoxing() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 1, 2); // Includes {1.5f, 2.5f}
        List<Float> collected = new ArrayList<>();

        slice.forEach(collected::add);

        assertEquals(2, collected.size(), "Collected list should contain two elements.");
        assertEquals(Float.valueOf(2f), collected.get(0));
        assertEquals(Float.valueOf(3f), collected.get(1));
    }

    @Test
    void testForEachLambdaSum() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, 3); // Slicing {2f, 3f, 4f}
        final float[] sum = {0f}; // Use array to bypass lambda final or effectively final limitation

        slice.forEachFloat(value -> sum[0] += value);

        assertEquals(6f, sum[0], "The sum of the elements should be 6");
    }

    @Test
    void testEmptySlice() {
        IFloatSlice slice = new FloatSlice(sourceLen10, 1, 0); // Empty slice

        slice.forEach(value -> fail("There should be no values to iterate over in an empty slice"));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> slice.get(0), "Accessing any element should throw an exception");
    }

    @Test
    void usecase1(){
        IFloatSlice slice = new FloatSlice(sourceLen10, 0, sourceLen10.length);
        float sumFormSlice = 0;
        for(float f : slice){
            sumFormSlice += f;
        }
        float sumFromOG = 0;
        for(float f : sourceLen10){
            sumFromOG += f;
        }

        assertEquals(sumFormSlice, sumFromOG);
    }




}