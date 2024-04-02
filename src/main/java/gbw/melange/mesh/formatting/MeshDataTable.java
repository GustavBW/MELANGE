package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import gbw.melange.mesh.constants.KnownAttributes;
import gbw.melange.mesh.constants.VertAttr;
import gbw.melange.shading.errors.Error;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Function;

//Doesn't currently support write operations, although that'd probably be nice
public class MeshDataTable implements IMeshDataTable {
    private static final Logger log = LogManager.getLogger();

    private final LinkedHashMap<VertAttr, float[]> vertexDataTable;
    //Whenever something is extracted, a reference is stored to the extracted list
    //So that, on modification, the list given to the outside, is updated simultaneously
    private record AttrTypeKeyPair<T extends IRefAccVec>(VertAttr attr, Class<T> clazz){}
    private final Map<AttrTypeKeyPair<?>, WeakReference<List<? extends IRefAccVec>>> latestRetrievals = new HashMap<>();
    private short[] indicies;
    private int vertexCount;

    private MeshDataTable(LinkedHashMap<VertAttr, float[]> dataTable, int vertexCount, short[] indicies){
        this.indicies = indicies;
        this.vertexCount = vertexCount;
        this.vertexDataTable = dataTable;
    }

    @Override
    public List<IRefAccVec2> extractVector2(VertAttr key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        AttrTypeKeyPair<IRefAccVec2> combinedKey = new AttrTypeKeyPair<>(key, IRefAccVec2.class);
        return retrieve0(IRefAccVec::createVec2, combinedKey, 2);
    }
    @Override
    public List<IRefAccVec3> extractVector3(VertAttr key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        AttrTypeKeyPair<IRefAccVec3> combinedKey = new AttrTypeKeyPair<>(key, IRefAccVec3.class);
        return retrieve0(IRefAccVec::createVec3, combinedKey, 3);
    }
    @Override
    public List<IRefAccVec4> extractVector4(VertAttr key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        AttrTypeKeyPair<IRefAccVec4> combinedKey = new AttrTypeKeyPair<>(key, IRefAccVec4.class);
        return retrieve0(IRefAccVec::createVec4, combinedKey, 4);
    }

    @Override
    public void add(IMeshDataTable other) {
        throw new RuntimeException("How 'bout u implement this first.");
    }

    @Override
    public Error addOrReplaceAttribute(VertAttr attr, int startIndex, float[] data) {
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
    public Error addOrReplaceAttribute(VertAttr attr, int startIndex, float[] data, float fillValue) {
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
        List<IRefAccVec3> positionData = extractVector3(KnownAttributes.POSITION, vertexCount);
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < indicies.length; i += 3) {
            // Ensure there are at least 3 indices left to form a face
            if (i + 2 < indicies.length) {
                IRefAccVec3 v1 = positionData.get(indicies[i] & 0xFFFF); // & 0xFFFF converts short to unsigned
                IRefAccVec3 v2 = positionData.get(indicies[i + 1] & 0xFFFF);
                IRefAccVec3 v3 = positionData.get(indicies[i + 2] & 0xFFFF);
                faces.add(new Face(v1, v2, v3));
            }
        }

        return faces;
    }
    private static final Function<IRefAccVec3, List<Face>> faceListProvider = k -> new ArrayList<>();
    @Override
    public List<Face> calculateFaces(Map<IRefAccVec3, List<Face>> allFacesOfVert) {
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
        Set<VertAttr> keyset = vertexDataTable.keySet();
        int vertexSizeInFloats = 0;
        for(VertAttr attr : keyset){
            vertexSizeInFloats += attr.compCount();
        }

        // Then, calculate the size of the flattened array
        float[] vertices = new float[vertexSizeInFloats * vertexCount];

        // Iterate through each attribute, adding its data to the correct position in the vertices array
        int currentOffset = 0;
        for (Map.Entry<VertAttr, float[]> entry : vertexDataTable.entrySet()) {
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
        out.setIndices(indicies);

        return out;
    }

    @Override
    public boolean checkExtraction(VertAttr key, int expectedOutputLength, String transformingTo) {
        if (!vertexDataTable.containsKey(key)) {
            MeshDataTable.log.debug("Tried to extract " + key + " from " + vertexDataTable + " as " + transformingTo + ", but no entry was present");
            return false;
        }
        final int entryLen = vertexDataTable.get(key).length;
        if (entryLen / key.compCount() != expectedOutputLength) {
            MeshDataTable.log.warn("Available data for " + key + " len: " + entryLen + " cannot be safely transformed to a list of " + transformingTo + ". Input -1 for expectedOutputLength to disable this check.");
            return false;
        }
        return true;
    }

    @Override
    public IMeshDataTable copy() {
        LinkedHashMap<VertAttr, float[]> copy = new LinkedHashMap<>();
        for(VertAttr key : vertexDataTable.keySet()){
            float[] original = vertexDataTable.get(key);
            final float[] entryCopy = new float[original.length];
            System.arraycopy(original, 0, entryCopy, 0, original.length);
            copy.put(key, entryCopy);
        }
        final short[] indiciesCopy = new short[indicies.length];
        System.arraycopy(this.indicies, 0, indiciesCopy, 0, this.indicies.length);

        return MeshDataTable.from(copy, this.vertexCount, indiciesCopy);
    }

    public static MeshDataTable from(Mesh mesh)  {
        if (mesh == null) {
            log.warn("Mesh is null");
            return from(new LinkedHashMap<>(), 0, new short[0]);
        }

        //Raw data
        float[] vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];
        mesh.getVertices(vertices);

        short[] indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);

        VertexAttributes attrs = mesh.getVertexAttributes();
        List<VertAttr> mappedAttr = KnownAttributes.convert(attrs);

        if(mappedAttr.size() != attrs.size()){
            log.warn("Unable to map vertex attributes");
            return from(new LinkedHashMap<>(), 0, new short[0]);
        }

        Map<VertAttr, Integer> totalAttrOffset = new HashMap<>();
        int totalOffsetOfPrevious = 0;
        for(VertAttr attr : mappedAttr){
            totalAttrOffset.put(attr, totalOffsetOfPrevious);
            totalOffsetOfPrevious += attr.compCount();
        }

        //Insertion Ordered
        final LinkedHashMap<VertAttr, float[]> vertexDataTable = new LinkedHashMap<>();
        // Assuming `totalAttrOffset` and `mappedAttr` are correctly populated
        for(VertAttr attr : mappedAttr){
            // Initialize the array to hold data for all vertices of this attribute
            float[] data = new float[mesh.getNumVertices() * attr.compCount()];
            vertexDataTable.put(attr, data);
        }

        // The size of a single vertex's data in floats, assuming it's the sum of the components of all attributes
        int vertexSize = totalOffsetOfPrevious; // This is effectively the stride

        for (int vertexIndex = 0; vertexIndex < mesh.getNumVertices(); vertexIndex++) {
            // Calculate the starting index for this vertex's data
            int baseIndex = vertexIndex * vertexSize;
            for (VertAttr attr : mappedAttr) {
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
    public static MeshDataTable from(LinkedHashMap<VertAttr, float[]> dataTable, int vertexCount, short[] indicies){
        return new MeshDataTable(dataTable, vertexCount, indicies);
    }


    /**
     * Any function that takes in 2 params and returns some instance of type R
     * <pre>
     *     {@code (t, s) -> new R(t, s)  ||  R::new }
     * </pre>
     * @param <T> in
     * @param <S> in
     * @param <R> out
     */
    @FunctionalInterface
    private interface BiProvider<T,S,R>{
        R create(T t, S s);
    }
    @SuppressWarnings("unchecked")
    private <T extends IRefAccVec> List<T> retrieve0(BiProvider<float[],Integer,T> provider, AttrTypeKeyPair<T> cacheKey, int entryWidth){
        if(latestRetrievals.containsKey(cacheKey)){
            WeakReference<List<? extends IRefAccVec>> asRetrieved = latestRetrievals.get(cacheKey);
            if(asRetrieved.get() != null){
                //Type safety? Who needs type safety anyways.
                //Jokes aside, due to the way all inserts into the cache map are controlled (through this very method),
                //it should at all times be safe to make this cast. If however, an insertion occurs outside this method
                //in the future, this code needs to be revised.
                return (List<T>) asRetrieved.get();
            }
        }

        final float[] data = vertexDataTable.get(cacheKey.attr());
        final List<T> vectors = new ArrayList<>();

        for (int i = 0; i < data.length; i += entryWidth) {
            vectors.add(provider.create(data, i));
        }
        latestRetrievals.put(cacheKey, new WeakReference<>(vectors));

        return vectors;
    }
}
