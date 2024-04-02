package gbw.melange.mesh.formatting;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import gbw.melange.mesh.constants.EVertexAttribute;
import gbw.melange.shading.errors.Error;
import jdk.jshell.spi.ExecutionControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Function;

//Doesn't currently support write operations, although that'd probably be nice
public class MeshDataTable implements IMeshDataTable {
    private static final Logger log = LogManager.getLogger();

    private final LinkedHashMap<EVertexAttribute, float[]> vertexDataTable;
    //Whenever something is extracted, a reference is stored to the extracted list
    //So that, on modification, the list given to the outside, is updated simultaneously
    private record AttrTypeKeyPair<T extends IRefAccVec>(EVertexAttribute attr, Class<T> clazz){}
    private final Map<AttrTypeKeyPair<?>, WeakReference<List<? extends IRefAccVec>>> latestRetrievals = new HashMap<>();
    private short[] indicies;
    private int vertexCount;

    private MeshDataTable(LinkedHashMap<EVertexAttribute, float[]> dataTable, int vertexCount, short[] indicies){
        this.indicies = indicies;
        this.vertexCount = vertexCount;
        this.vertexDataTable = dataTable;
    }

    @Override
    public List<IRefAccVec2> extractVector2(EVertexAttribute key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        AttrTypeKeyPair<IRefAccVec2> combinedKey = new AttrTypeKeyPair<>(key, IRefAccVec2.class);
        return retrieve0(IRefAccVec::createVec2, combinedKey, 2);
    }
    @Override
    public List<IRefAccVec3> extractVector3(EVertexAttribute key, int expectedOutputLength) {
        if (expectedOutputLength != -1) {
            if (!checkExtraction(key, expectedOutputLength, "Vector3")) {
                return new ArrayList<>();
            }
        }

        AttrTypeKeyPair<IRefAccVec3> combinedKey = new AttrTypeKeyPair<>(key, IRefAccVec3.class);
        return retrieve0(IRefAccVec::createVec3, combinedKey, 3);
    }
    @Override
    public List<IRefAccVec4> extractVector4(EVertexAttribute key, int expectedOutputLength) {
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
    public Error addOrReplaceAttribute(EVertexAttribute attr, int startIndex, float[] data) {
        // Check if the attribute exists and calculate the required size of the data array
        final int componentCount = attr.componentCount();
        final int requiredSize = vertexCount * componentCount;

        // Existing data check
        final float[] existingData = vertexDataTable.get(attr);

        if (startIndex != 0 && existingData == null) {
            log.error("Start index is not 0 and no existing data found for the attribute: " + attr);
            return new Error("Unable to insert new data from index " + startIndex + " as there is no prior data.");
        }

        if (data.length + startIndex * componentCount < requiredSize) {
            log.error("Insufficient amount of values provided for the attribute: " + attr);
            return new Error("Unable to insert new data from index " + startIndex +
                    " as there is too little provided data. Provided data.length: " + data.length +
                    " amount required: " + (existingData.length - startIndex));
        }

        if (data.length + startIndex * componentCount > requiredSize) {
            log.debug("Excess data provided for the attribute: " + attr + ". The remainder will be ignored.");
        }

        if(existingData == null || existingData.length == 0){
            vertexDataTable.put(attr, data);
            return Error.NONE;
        }

        if(startIndex < 0 || startIndex > existingData.length - 1){
            return new Error("Invalid start index. Kindly be within bounds");
        }

        // Calculate the number of elements to copy taking into account the startIndex and ensuring no out of bounds
        int numElementsToCopy = Math.min(data.length, existingData.length - startIndex * attr.componentCount());

        //copy data from 0 into existingData from startIndex while the amount of entries copied is less than len(existing) - startIndex
        System.arraycopy(data, 0, existingData, startIndex * attr.componentCount(), numElementsToCopy);

        return Error.NONE;
    }

    @Override
    public Error addOrReplaceAttribute(EVertexAttribute attr, int startIndex, float[] data, float fillValue) {


        return Error.NONE;
    }

    @Override
    public List<Face> calculateFaces() {
        List<IRefAccVec3> positionData = extractVector3(EVertexAttribute.POSITION, vertexCount);
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
        Set<EVertexAttribute> keyset = vertexDataTable.keySet();
        int vertexSizeInFloats = 0;
        for(EVertexAttribute attr : keyset){
            vertexSizeInFloats += attr.componentCount();
        }

        // Then, calculate the size of the flattened array
        float[] vertices = new float[vertexSizeInFloats * vertexCount];

        // Iterate through each attribute, adding its data to the correct position in the vertices array
        int currentOffset = 0;
        for (Map.Entry<EVertexAttribute, float[]> entry : vertexDataTable.entrySet()) {
            float[] attributeData = entry.getValue();
            int componentCount = entry.getKey().componentCount();

            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                System.arraycopy(attributeData, vertexIndex * componentCount, vertices,
                        vertexIndex * vertexSizeInFloats + currentOffset, componentCount);
            }
            currentOffset += componentCount;
        }
        // Create the array of VertexAttributes for the Mesh
        VertexAttribute[] vertexAttributes = EVertexAttribute.convert(keyset);

        // Finally, create the Mesh
        Mesh out = new Mesh(true, vertexCount, indicies.length, vertexAttributes);
        out.setVertices(vertices);
        out.setIndices(indicies);

        return out;
    }

    @Override
    public boolean checkExtraction(EVertexAttribute key, int expectedOutputLength, String transformingTo) {
        if (!vertexDataTable.containsKey(key)) {
            MeshDataTable.log.debug("Tried to extract " + key + " from " + vertexDataTable + " as " + transformingTo + ", but no entry was present");
            return false;
        }
        final int entryLen = vertexDataTable.get(key).length;
        if (entryLen / key.componentCount() != expectedOutputLength) {
            MeshDataTable.log.warn("Available data for " + key + " len: " + entryLen + " cannot be safely transformed to a list of " + transformingTo + ". Input -1 for expectedOutputLength to disable this check.");
            return false;
        }
        return true;
    }

    @Override
    public IMeshDataTable copy() {
        LinkedHashMap<EVertexAttribute, float[]> copy = new LinkedHashMap<>();
        for(EVertexAttribute key : vertexDataTable.keySet()){
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
        List<EVertexAttribute> mappedAttr = EVertexAttribute.convert(attrs);

        if(mappedAttr.size() != attrs.size()){
            log.warn("Unable to map vertex attributes");
            return from(new LinkedHashMap<>(), 0, new short[0]);
        }

        Map<EVertexAttribute, Integer> totalAttrOffset = new HashMap<>();
        int totalOffsetOfPrevious = 0;
        for(EVertexAttribute attr : mappedAttr){
            totalAttrOffset.put(attr, totalOffsetOfPrevious);
            totalOffsetOfPrevious += attr.componentCount();
        }

        //Insertion Ordered
        final LinkedHashMap<EVertexAttribute, float[]> vertexDataTable = new LinkedHashMap<>();
        // Assuming `totalAttrOffset` and `mappedAttr` are correctly populated
        for(EVertexAttribute attr : mappedAttr){
            // Initialize the array to hold data for all vertices of this attribute
            float[] data = new float[mesh.getNumVertices() * attr.componentCount()];
            vertexDataTable.put(attr, data);
        }

        // The size of a single vertex's data in floats, assuming it's the sum of the components of all attributes
        int vertexSize = totalOffsetOfPrevious; // This is effectively the stride

        for (int vertexIndex = 0; vertexIndex < mesh.getNumVertices(); vertexIndex++) {
            // Calculate the starting index for this vertex's data
            int baseIndex = vertexIndex * vertexSize;
            for (EVertexAttribute attr : mappedAttr) {
                if (attr.componentCount() <= 0) continue;

                // Get the array where we'll store this attribute's data
                float[] attributeData = vertexDataTable.get(attr);
                // Calculate the starting index for this attribute's data within the vertex
                int attrStartIndex = baseIndex + totalAttrOffset.get(attr);
                // Extract the data and store it
                System.arraycopy(vertices, attrStartIndex, attributeData, vertexIndex * attr.componentCount(), attr.componentCount());
            }
        }

        return from(vertexDataTable, mesh.getNumVertices(), indices);
    }
    public static MeshDataTable from(LinkedHashMap<EVertexAttribute, float[]> dataTable, int vertexCount, short[] indicies){
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
