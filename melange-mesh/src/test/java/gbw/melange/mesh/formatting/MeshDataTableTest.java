package gbw.melange.mesh.formatting;

import gbw.melange.common.mesh.formatting.IMeshDataTable;
import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.common.mesh.constants.IVertAttr;
import gbw.melange.mesh.constants.KnownAttributes;
import gbw.melange.mesh.constants.VertAttr;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeshDataTableTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void extractionConversionAccuracy() {
        IMeshDataTable table = getTable();
        List<IVertAttr<?>> attrList = List.of(KnownAttributes.values());
        for(IVertAttr<?> attr : attrList){
            assertDoesNotThrow(() -> table.extract(attr), "It must not throw regardless. Its fallback is an empty list");
        }

        List<ISliceVec3> positionSlices = table.extract(KnownAttributes.POSITION);
        assertNotNull(positionSlices, "Extracted data should not be null.");
        assertEquals(positionData.length / 3, positionSlices.size(), "For a three component representation, there should be 4 slices.");

        String msg = "All data must be retained and accessible through slices";
        assertEquals(positionData[0], positionSlices.get(0).x(), msg);
        assertEquals(positionData[1], positionSlices.get(0).y(), msg);
        assertEquals(positionData[2], positionSlices.get(0).z(), msg);

        assertEquals(positionData[3], positionSlices.get(1).x(), msg);
        assertEquals(positionData[4], positionSlices.get(1).y(), msg);
        assertEquals(positionData[5], positionSlices.get(1).z(), msg);

        assertEquals(positionData[6], positionSlices.get(2).x(), msg);
        assertEquals(positionData[7], positionSlices.get(2).y(), msg);
        assertEquals(positionData[8], positionSlices.get(2).z(), msg);

        assertEquals(positionData[9], positionSlices.get(3).x(), msg);
        assertEquals(positionData[10], positionSlices.get(3).y(), msg);
        assertEquals(positionData[11], positionSlices.get(3).z(), msg);
    }
    @Test
    void extractNonExistentAttribute() {
        IMeshDataTable table = getTable();
        List<ISliceVec2> nonExisting = table.extract(KnownAttributes.BONE_WEIGHT);
        assertTrue(nonExisting.isEmpty(), "Attempting to extract a non-existent attribute should give an empty list.");

    }

    @Test
    void anyExtractedIsCached() {
        IMeshDataTable table = getTable();
        List<ISliceVec3> firstExtraction = table.extract(KnownAttributes.POSITION);
        List<ISliceVec3> secondExtraction = table.extract(KnownAttributes.POSITION);
        assertSame(firstExtraction, secondExtraction, "Subsequent extractions should return the same cached list.");

        for(int i = 0; i < firstExtraction.size(); i++){
            assertSame(firstExtraction.get(i), secondExtraction.get(i), "All slices should be the same too");
        }

        // Now modify the data for POSITION and extract again to see if cache updates.
        // Verify the cache is updated as expected.
    }

    @Test
    void cacheInvalidation(){

    }

    @Test
    void extractionTolerances(){
        IMeshDataTable table = getTable();

        //If either of compCount, alias, or usage changes, the data should not be extractable. For safety reasons
        //COMP COUNT CHANGED
        IVertAttr<ISliceVec3> twoCompPosition = new VertAttr<>(
                2,
                KnownAttributes.POSITION.alias(),
                KnownAttributes.POSITION.usage(),
                KnownAttributes.POSITION.repressentativeClass(),
                KnownAttributes.POSITION.representationProvider()
        );
        List<ISliceVec3> compCountChanged = table.extract(twoCompPosition);
        assertNotNull(compCountChanged, "The fallback should still be an empty list");
        assertTrue(compCountChanged.isEmpty(), "Data stored under some 3 identifiers - alias, usage and compCount, should not be extractable with any changes to these in the key.");

        //MISSPELLED ALIAS
        IVertAttr<ISliceVec3> misspelledAliasPosition = new VertAttr<>(
                KnownAttributes.POSITION.compCount(),
                "u_position",
                KnownAttributes.POSITION.usage(),
                KnownAttributes.POSITION.repressentativeClass(),
                KnownAttributes.POSITION.representationProvider()
        );
        List<ISliceVec3> aliasChanged = table.extract(misspelledAliasPosition);
        assertNotNull(aliasChanged, "The fallback should still be an empty list");
        assertTrue(aliasChanged.isEmpty(), "Data stored under some 3 identifiers - alias, usage and compCount, should not be extractable with any changes to these in the key.");

        //USAGE OFF BY ONE
        IVertAttr<ISliceVec3> offByOneUsagePosition = new VertAttr<>(
                KnownAttributes.POSITION.compCount(),
                KnownAttributes.POSITION.alias(),
                KnownAttributes.POSITION.usage() - 1,
                KnownAttributes.POSITION.repressentativeClass(),
                KnownAttributes.POSITION.representationProvider()
        );
        List<ISliceVec3> usageChanged = table.extract(offByOneUsagePosition);
        assertNotNull(usageChanged, "The fallback should still be an empty list");
        assertTrue(usageChanged.isEmpty(), "Data stored under some 3 identifiers - alias, usage and compCount, should not be extractable with any changes to these in the key.");
    }
    @Test
    void attrRepAmbiguity(){
        IMeshDataTable table = getTable();
        //However representing class and provider should be a free for all
        IVertAttr<IFloatSlice> changedRepClass = new VertAttr<>(
                KnownAttributes.POSITION.compCount(),
                KnownAttributes.POSITION.alias(),
                KnownAttributes.POSITION.usage(),
                IFloatSlice.class,
                IFloatSlice.create
        );
        List<IFloatSlice> changedRepRes = table.extract(changedRepClass);
        assertNotNull(changedRepRes, "The fallback should still be an empty list");
        assertEquals(4, changedRepRes.size(), "Even though the representing class was changed, that is not part of the hashcode function and the same entries should be returned.");
        for(IFloatSlice slice : changedRepRes){
            assertEquals(3, slice.entryWidth(), "Since comp count is the determining factor, all slices should have a width of 3.");
        }
        List<ISliceVec3> asIfPositionAttr = table.extract(KnownAttributes.POSITION);
        assertSame(asIfPositionAttr,changedRepRes, "Furthermore, it should be the exact same lists - which is possible due to an ungodly amount of inheritance and generics shenanigans");
        for(int i = 0; i < asIfPositionAttr.size(); i++){
            assertSame(asIfPositionAttr.get(i), changedRepRes.get(i), "Every single slice should be the same, although referenced differently depending on the key.");
        }
    }

    @Test
    void attrRepID10THandling(){

    }

    @Test
    void copyAttributeData(){
        IMeshDataTable table = getTable();
        final float[] nonExistingEntry = table.copyAttributeData(KnownAttributes.BONE_WEIGHT);
        assertEquals(0, nonExistingEntry.length, "An empty array should be returned if no data is available.");

        final float[] copyOfPositionData = table.copyAttributeData(KnownAttributes.POSITION);
        assertEquals(positionData.length, copyOfPositionData.length, "No values must be missing from the original data.");
        assertNotSame(positionData, copyOfPositionData, "A new float array instance must be returned. Not the original.");

        for(int i = 0; i < copyOfPositionData.length; i++){
            assertEquals(positionData[i], copyOfPositionData[i], "Order and values should be retained on copy.");
        }
    }


    private static IMeshDataTable getTable(){
        LinkedHashMap<IVertAttr<?>,float[]> rawTable = new LinkedHashMap<>();
        int[] indicies = {0,1,2,0,1,3};
        rawTable.put(KnownAttributes.POSITION, positionData);
        rawTable.put(KnownAttributes.UV, uvData);
        rawTable.put(KnownAttributes.COLOR_UNPACKED, colorUnpackedData);
        rawTable.put(KnownAttributes.NORMAL, normalData);
        rawTable.put(KnownAttributes.TANGENT, tangentData);

        return MeshDataTable.from(rawTable, 4, indicies);
    }

    private static final float[] positionData = new float[]{
            -1.0f, -1.0f, 0.0f, // Vertex 0
            1.0f, -1.0f, 0.0f,  // Vertex 1
            -1.0f, 1.0f, 0.0f,  // Vertex 2
            1.0f, 1.0f, 0.0f    // Vertex 3
    };
    private static final float[] uvData = new float[]{
            0.0f, 0.0f, // Vertex 0
            1.0f, 0.0f, // Vertex 1
            0.0f, 1.0f, // Vertex 2
            1.0f, 1.0f  // Vertex 3
    };
    private static final float[] colorUnpackedData = new float[]{
            1.0f, 1.0f, 1.0f, 1.0f, // Vertex 0
            1.0f, 1.0f, 1.0f, 1.0f, // Vertex 1
            1.0f, 1.0f, 1.0f, 1.0f, // Vertex 2
            1.0f, 1.0f, 1.0f, 1.0f  // Vertex 3
    };
    private static final float[] normalData = new float[]{
            0.0f, 0.0f, 1.0f, // Vertex 0
            0.0f, 0.0f, 1.0f, // Vertex 1
            0.0f, 0.0f, 1.0f, // Vertex 2
            0.0f, 0.0f, 1.0f  // Vertex 3
    };
    private static final float[] tangentData = new float[]{
            1.0f, 0.0f, 0.0f, // Vertex 0
            1.0f, 0.0f, 0.0f, // Vertex 1
            1.0f, 0.0f, 0.0f, // Vertex 2
            1.0f, 0.0f, 0.0f  // Vertex 3
    };
}