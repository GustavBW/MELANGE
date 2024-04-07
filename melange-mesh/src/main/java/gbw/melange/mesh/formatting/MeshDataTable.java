package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import gbw.melange.common.mesh.formatting.Face;
import gbw.melange.common.mesh.formatting.IMeshDataTable;
import gbw.melange.common.mesh.formatting.MultiTypeTableChecker;
import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.common.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.common.mesh.constants.IVertAttr;
import gbw.melange.mesh.constants.KnownAttributes;
import gbw.melange.common.errors.Error;
import gbw.melange.common.mesh.formatting.providers.SliceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Function;

/**
 * @author GustavBW
 */
public class MeshDataTable implements IMeshDataTable {
    private static final Logger log = LogManager.getLogger();

    private final LinkedHashMap<IVertAttr<?>, float[]> vertexDataTable;

    /**
     * On any extraction operation, the resulting list of Slices are cached as to not accidentally recreate the same list of Slices.
     * (Since slices are immutable and just referencing the underlying source, which is an immutable reference, it makes no sense to accidentally create two different lists.)
     */
    private final Map<IVertAttr<?>, WeakReference<List<? extends IFloatSlice>>> retrievalCache = new HashMap<>();
    private int[] indicies;
    private int vertexCount;

    private MeshDataTable(LinkedHashMap<IVertAttr<?>, float[]> dataTable, int vertexCount, int[] indicies){
        this.indicies = indicies;
        this.vertexCount = vertexCount;
        this.vertexDataTable = dataTable;
    }
    @Override
    public <T extends IFloatSlice> List<T> extract(IVertAttr<T> key){
        Error keyErr = MultiTypeTableChecker.checkKey(key);
        if(keyErr != Error.NONE){
            log.warn(keyErr.msg());
            return new ArrayList<>();
        }
        return retrieve0(key.representationProvider(), key);
    }
    @Override
    public <T extends IFloatSlice> List<T> extractCopy(IVertAttr<T> attr){
        List<T> currentExtraction = extract(attr);
        final List<T> copy = new ArrayList<>(currentExtraction.size());
        for(T slice : currentExtraction){
            copy.add((T) slice.copy());
        }
        return copy;
    }
    @Override
    public int[] getIndicies(){
        return this.indicies;
    }
    @Override
    public Set<IVertAttr<? extends IFloatSlice>> getVertexAttributes() {
        return vertexDataTable.keySet();
    }
    @Override
    public float[] copyAttributeData(IVertAttr<?> attr){
        if(!vertexDataTable.containsKey(attr)){
            return new float[0];
        }
        final float[] source = vertexDataTable.get(attr);
        return Arrays.copyOf(source, source.length);
    }

    @Override
    public <T extends IFloatSlice> List<T> dropAttribute(IVertAttr<T> attr) {
        // Extract any current data for the attribute before removing it.
        List<T> extractedData = this.extract(attr);

        // Remove the attribute from the table.
        vertexDataTable.remove(attr);
        //Clearing cache
        retrievalCache.remove(attr);

        // Return the extracted data.
        return extractedData;
    }

    @Override
    public Error add(IMeshDataTable other) {
        Error compatibilityErr = checkTableCompatibility(other);
        if(compatibilityErr != Error.NONE){
            return compatibilityErr;
        }

        Set<IVertAttr<? extends IFloatSlice>> currentKeySetThis = getVertexAttributes();
        //System.arraycopy go brr
        for(IVertAttr<? extends IFloatSlice> attr : currentKeySetThis){
            //Check if the value in other can be correctly interpreted by the attrKey in this
            final float[] dataOfOther = other.copyAttributeData(attr);
            if(dataOfOther.length % attr.compCount() != 0){
                return new Error("Unable to accurately transfer attribute " + attr.alias() +
                        " from " + other + " as the array length: " + dataOfOther.length + " cannot be correctly sliced into vectors of " +
                        attr.compCount() + " components.");
            }

            final float[] currentData = vertexDataTable.get(attr);
            final float[] combined = new float[currentData.length + dataOfOther.length];
            System.arraycopy(currentData, 0, combined, 0, currentData.length);
            System.arraycopy(dataOfOther, 0, combined, currentData.length, dataOfOther.length);

            updateCachedSlicesOf(attr, combined, currentData.length);

            vertexDataTable.put(attr, combined);
        }

        //Update indicies

        final int[] combinedIndicies = new int[this.indicies.length + other.getIndicies().length];
        System.arraycopy(this.indicies, 0, combinedIndicies, 0, this.indicies.length);
        System.arraycopy(other.getIndicies(), 0, combinedIndicies, this.indicies.length, other.getIndicies().length);

        this.indicies = combinedIndicies;
        this.vertexCount += other.getVertexCount();

        return Error.NONE;
    }

    private Error checkTableCompatibility(IMeshDataTable other){
        //Check keys
        Set<IVertAttr<?>> keysOfThis = new HashSet<>(getVertexAttributes());
        Set<IVertAttr<?>> keysOfOther = new HashSet<>(other.getVertexAttributes());

        // Find attributes missing in this
        Set<IVertAttr<?>> missingInThis = new HashSet<>(keysOfOther);
        missingInThis.removeAll(keysOfThis);

        // Find attributes missing in 'keysOfOther'
        Set<IVertAttr<?>> missingInOther = new HashSet<>(keysOfThis);
        missingInOther.removeAll(keysOfOther);

        if (!(missingInThis.isEmpty() && missingInOther.isEmpty())) {
            String missingInThisMsg = missingInThis.isEmpty() ? "" : "Missing in current current: " + missingInThis + ". ";
            String missingInOtherMsg = missingInOther.isEmpty() ? "" : "Missing in added table: " + missingInOther + ".";
            return new Error("Datatables do not have matching attributes. " + missingInThisMsg + missingInOtherMsg);
        }
        return Error.NONE;
    }

    @Override
    public <T extends IFloatSlice> Error addOrReplaceAttribute(IVertAttr<T> attr, int startIndex, float[] data) {
        final float[] existingData = vertexDataTable.get(attr);
        final int componentCount = attr.compCount();

        // If the attribute does not exist and startIndex is not zero, return an error
        if (startIndex != 0 && (existingData == null || existingData.length == 0)) {
            log.error("Start index is not 0 and no existing data found for the attribute: " + attr);
            return new Error("Unable to insert new data from index " + startIndex + " as there is no prior data.");
        }

        // Calculate the actual starting index in the array, based on the vertex number and component count
        int actualStartIndex = startIndex * componentCount;
        if (actualStartIndex < 0 || (existingData != null && actualStartIndex >= existingData.length)) {
            return new Error("Invalid start index. Kindly be within bounds");
        }

        // Determine the amount of data that can actually be inserted
        int insertableDataLength = data.length;
        if (existingData != null) {
            insertableDataLength = Math.min(data.length, existingData.length - actualStartIndex);
        }

        // If there's not enough data to insert from the specified startIndex, return an error
        if (existingData != null && insertableDataLength + actualStartIndex < existingData.length) {
            log.error("Insufficient amount of values provided for the attribute: " + attr);
            return new Error("Insufficient data provided. Provided length: " + data.length + ", required: " + (existingData.length - actualStartIndex));
        }

        // Insert or replace the attribute data
        if (existingData == null) {
            // Directly use the provided data if it's a new attribute or if the existing data is empty
            vertexDataTable.put(attr, data);
        } else {
            // Copy the provided data into the existing array, starting at the computed actualStartIndex
            System.arraycopy(data, 0, existingData, actualStartIndex, insertableDataLength);
        }

        return Error.NONE;
    }

    @Override
    public <T extends IFloatSlice> Error addOrReplaceAttribute(IVertAttr<T> attr, int startIndex, float[] data, float fillValue) {
        final float[] existingData = vertexDataTable.get(attr);
        final int componentCount = attr.compCount();
        int actualStartIndex = startIndex * componentCount;

        // Determine total required length based on the vertex count and component count
        int requiredLength = vertexCount * componentCount;

        // If no existing data or starting anew, initialize or resize data array
        if (existingData == null || existingData.length == 0) {
            float[] newData = new float[requiredLength];

            // Fill the newData array with fillValue up to the actualStartIndex, if startIndex is not 0
            Arrays.fill(newData, 0, actualStartIndex, fillValue);

            // Copy the provided data into newData, starting at actualStartIndex
            int lengthToCopy = Math.min(data.length, newData.length - actualStartIndex);
            System.arraycopy(data, 0, newData, actualStartIndex, lengthToCopy);

            // If there's still space left after copying data, fill the rest with fillValue
            if (actualStartIndex + lengthToCopy < newData.length) {
                Arrays.fill(newData, actualStartIndex + lengthToCopy, newData.length, fillValue);
            }

            vertexDataTable.put(attr, newData);
            return Error.NONE;
        }

        // For existing data, ensure startIndex is within bounds
        if (actualStartIndex >= existingData.length) {
            return new Error("Invalid start index. Kindly be within bounds");
        }

        // Calculate the number of elements to copy taking into account the startIndex
        int numElementsToCopy = Math.min(data.length, existingData.length - actualStartIndex);

        // Copy data into existingData, starting at the computed actualStartIndex
        System.arraycopy(data, 0, existingData, actualStartIndex, numElementsToCopy);

        // If there's space left in existingData after the copy operation, fill it with fillValue
        if (actualStartIndex + numElementsToCopy < existingData.length) {
            Arrays.fill(existingData, actualStartIndex + numElementsToCopy, existingData.length, fillValue);
        }

        return Error.NONE;
    }

    @Override
    public List<Face> calculateFaces() {
        List<ISliceVec3> positionData = extract(KnownAttributes.POSITION);
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < indicies.length; i += 3) {
            // Ensure there are at least 3 indices left to form a face
            if (i + 2 < indicies.length) {
                ISliceVec3 v1 = positionData.get(indicies[i]);
                ISliceVec3 v2 = positionData.get(indicies[i + 1]);
                ISliceVec3 v3 = positionData.get(indicies[i + 2]);
                faces.add(new Face(v1, v2, v3));
            }
        }

        return faces;
    }
    private static final Function<ISliceVec3, List<Face>> faceListProvider = k -> new ArrayList<>();
    @Override
    public List<Face> calculateFaces(Map<ISliceVec3, List<Face>> allFacesOfVert) {
        final List<Face> faces = calculateFaces();
        for (Face face : faces){
            allFacesOfVert.computeIfAbsent(face.v0(), faceListProvider).add(face);
            allFacesOfVert.computeIfAbsent(face.v1(), faceListProvider).add(face);
            allFacesOfVert.computeIfAbsent(face.v2(), faceListProvider).add(face);
        }
        return faces;
    }
    @Override
    public Mesh convertToMesh() {
        // First, calculate the total size of a single vertex by summing the component counts
        Set<IVertAttr<?>> keyset = vertexDataTable.keySet();
        int vertexSizeInFloats = 0;
        for(IVertAttr<?> attr : keyset){
            vertexSizeInFloats += attr.compCount();
        }

        // Then, calculate the size of the flattened array
        float[] vertices = new float[vertexSizeInFloats * vertexCount];

        // Iterate through each attribute, adding its data to the correct position in the vertices array
        int currentOffset = 0;
        for (Map.Entry<IVertAttr<?>, float[]> entry : vertexDataTable.entrySet()) {
            float[] attributeData = entry.getValue();
            int componentCount = entry.getKey().compCount();

            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                System.arraycopy(attributeData, vertexIndex * componentCount, vertices,
                        vertexIndex * vertexSizeInFloats + currentOffset, componentCount);
            }
            currentOffset += componentCount;
        }
        // Create the array of VertexAttributes for the Mesh
        VertexAttribute[] vertexAttributes = KnownAttributes.convert(keyset);

        // Finally, create the Mesh
        Mesh out = new Mesh(true, vertexCount, indicies.length, vertexAttributes);
        out.setVertices(vertices);
        short[] meshIsLimited = new short[indicies.length];
        for(int i = 0; i < indicies.length; i++){
            meshIsLimited[i] = (short) indicies[i];
        }
        out.setIndices(meshIsLimited);

        return out;
    }
    @Override
    public Error checkExtraction(IVertAttr<?> key, int expectedOutputLength) {
        Error keyCheck = MultiTypeTableChecker.checkKey(key);
        if(keyCheck != Error.NONE){
            return keyCheck;
        }
        if (!vertexDataTable.containsKey(key)) {
            return new Error("Tried to extract " + key + " from " + vertexDataTable + " as " + key.repressentativeClass() + ", but no entry was present");
        }
        final int entryLen = vertexDataTable.get(key).length;
        if (entryLen / key.compCount() != expectedOutputLength) {
            return new Error("Available data for " + key + " len: " + entryLen + " cannot be safely transformed to a list of " + key.repressentativeClass() + ".");
        }
        return Error.NONE;
    }

    @Override
    public IMeshDataTable copy() {
        LinkedHashMap<IVertAttr<?>, float[]> copy = new LinkedHashMap<>();
        for(IVertAttr<?> key : vertexDataTable.keySet()){
            float[] original = vertexDataTable.get(key);
            final float[] entryCopy = new float[original.length];
            System.arraycopy(original, 0, entryCopy, 0, original.length);
            copy.put(key, entryCopy);
        }
        final int[] indiciesCopy = new int[indicies.length];
        System.arraycopy(this.indicies, 0, indiciesCopy, 0, this.indicies.length);

        return MeshDataTable.from(copy, this.vertexCount, indiciesCopy);
    }
    public static MeshDataTable from(Mesh mesh)  {
        if (mesh == null) {
            log.warn("Mesh is null");
            return from(new LinkedHashMap<>(), 0, new int[0]);
        }

        //Raw data
        float[] vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];
        mesh.getVertices(vertices);

        short[] indiciesTemp = new short[mesh.getNumIndices()];
        int[] indices = new int[mesh.getNumIndices()];
        mesh.getIndices(indiciesTemp);
        for(int i = 0; i < indiciesTemp.length; i++){
            indices[i] = indiciesTemp[i];
        }

        VertexAttributes attrs = mesh.getVertexAttributes();
        List<IVertAttr<?>> mappedAttr = KnownAttributes.convert(attrs);

        if(mappedAttr.size() != attrs.size()){
            log.warn("Unable to map vertex attributes");
            return from(new LinkedHashMap<>(), 0, new int[0]);
        }

        Map<IVertAttr<?>, Integer> totalAttrOffset = new HashMap<>();
        int totalOffsetOfPrevious = 0;
        for(IVertAttr<?> attr : mappedAttr){
            totalAttrOffset.put(attr, totalOffsetOfPrevious);
            totalOffsetOfPrevious += attr.compCount();
        }

        //Insertion Ordered
        final LinkedHashMap<IVertAttr<?>, float[]> vertexDataTable = new LinkedHashMap<>();
        // Assuming `totalAttrOffset` and `mappedAttr` are correctly populated
        for(IVertAttr<?> attr : mappedAttr){
            // Initialize the array to hold data for all vertices of this attribute
            float[] data = new float[mesh.getNumVertices() * attr.compCount()];
            vertexDataTable.put(attr, data);
        }

        // The size of a single vertex's data in floats, assuming it's the sum of the components of all attributes
        int vertexSize = totalOffsetOfPrevious; // This is effectively the stride

        for (int vertexIndex = 0; vertexIndex < mesh.getNumVertices(); vertexIndex++) {
            // Calculate the starting index for this vertex's data
            int baseIndex = vertexIndex * vertexSize;
            for (IVertAttr<?> attr : mappedAttr) {
                if (attr.compCount() <= 0) continue;

                // Get the array where we'll store this attribute's data
                float[] attributeData = vertexDataTable.get(attr);
                // Calculate the starting index for this attribute's data within the vertex
                int attrStartIndex = baseIndex + totalAttrOffset.get(attr);
                // Extract the data and store it
                System.arraycopy(vertices, attrStartIndex, attributeData, vertexIndex * attr.compCount(), attr.compCount());
            }
        }

        return from(vertexDataTable, mesh.getNumVertices(), indices);
    }
    public static MeshDataTable from(LinkedHashMap<IVertAttr<?>, float[]> dataTable, int vertexCount, int[] indicies){
        return new MeshDataTable(dataTable, vertexCount, indicies);
    }

    @Override
    public int getVertexCount(){
        return vertexCount;
    }

    //Mildly dubious stuff below. Approach with caution

    @SuppressWarnings("unchecked")
    private <T extends IFloatSlice> void updateCachedSlicesOf(IVertAttr<T> attr, float[] source, int fromIndex) {
        WeakReference<List<? extends IFloatSlice>> currentCacheValue = retrievalCache.get(attr);
        if(currentCacheValue == null){
            return;
        }
        List<T> currentCachedSlices = (List<T>) currentCacheValue.get();
        if (currentCachedSlices == null) {
            return;
        }
        for(IFloatSlice existingCachedSlice : currentCachedSlices){
            ((FloatSlice) existingCachedSlice).setSource(source);
        }
        for (int i = 0; i < source.length - fromIndex; i += attr.compCount()) {
            currentCachedSlices.add(attr.representationProvider().create(source, i + fromIndex, attr.compCount()));
        }
    }


    @SuppressWarnings("unchecked")
    private <T extends IFloatSlice> List<T> retrieve0(SliceProvider<T> provider, IVertAttr<T> key){
        if(retrievalCache.containsKey(key)){
            WeakReference<List<? extends IFloatSlice>> asRetrieved = retrievalCache.get(key);
            if(asRetrieved.get() != null){
                //Type safety? Who needs type safety.
                //Jokes aside, due to the way all inserts into the cache map are controlled (through this very method),
                //it should at all times be safe to make this cast. If however, an insertion occurs outside this method
                //in the future, this code needs to be revised.
                return (List<T>) asRetrieved.get();
            }
        }
        final int compCount = key.compCount();
        final float[] data = vertexDataTable.get(key);
        if(data == null){
            return new ArrayList<>();
        }

        final List<T> slices = new ArrayList<>(data.length / compCount);

        for (int i = 0; i < data.length; i += compCount) {
            slices.add(provider.create(data, i, compCount));
        }
        retrievalCache.put(key, new WeakReference<>(slices));

        return slices;
    }
}
