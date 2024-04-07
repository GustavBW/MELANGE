package gbw.melange.shading.components;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.shading.constants.GLDrawStyle;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.common.errors.Error;
import gbw.melange.shading.errors.DynamicRelinkingError;
import org.jetbrains.annotations.Contract;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class AtomicShader implements IAtomicShader {
    /**
     * The locations (int) of all uniforms (alias)
     */
    protected final Map<String,Integer> locations = new HashMap<>();
    public record ProgramHandle(int value){} //Typealias.
    private final ProgramHandle programHandle;
    private VertexShader vertex = null;
    private FragmentShader fragment = null;
    private GeometryShader geometry = null;
    //Add tesselation and tesselation control
    private BiConsumer<Mesh, Matrix4> renderFuncPointer = this::renderNONE;

    public AtomicShader(){
        this.programHandle = new ProgramHandle(GL30.glCreateProgram());
    }

    @Override
    public Error setVertex(VertexShader vertex, boolean deleteExisting) {
        Error preFlightErr = replacementPreFlightCheck(vertex);
        if(preFlightErr != Error.NONE){
            return preFlightErr;
        }
        handleRemoval(this.vertex, deleteExisting);

        this.vertex = vertex;

        recalculatePointer();
        attachAndReLink(vertex);

        return Error.NONE;
    }

    @Override
    public Error setFragment(FragmentShader fragment, boolean deleteExisting) {
        Error preFlightErr = replacementPreFlightCheck(fragment);
        if(preFlightErr != Error.NONE){
            return preFlightErr;
        }
        handleRemoval(this.fragment, deleteExisting);

        this.fragment = fragment;

        recalculatePointer();
        attachAndReLink(fragment);

        return Error.NONE;
    }

    @Override
    public Error setGeometry(GeometryShader geometry, boolean deleteExisting) {
        Error preFlightErr = replacementPreFlightCheck(geometry);
        if(preFlightErr != Error.NONE){
            return preFlightErr;
        }
        handleRemoval(this.geometry, deleteExisting);

        this.geometry = geometry;

        recalculatePointer();
        attachAndReLink(geometry);

        return Error.NONE;
    }



    /**
     * @param toBeAttached the shader to attach
     * @throws DynamicRelinkingError if the linking step fails
     */
    private void attachAndReLink(IShader toBeAttached){
        GL30.glAttachShader(programHandle.value(), toBeAttached.getHandle());
        GL30.glLinkProgram(programHandle.value());

        // Check link status
        if (GL30.glGetProgrami(programHandle.value(), GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
            // Linking failed, retrieve and throw detailed error message
            String errMsgToEndAllErrMsgs = "Linking failed for: + " + this + " when : " + toBeAttached + " was introduced. \n" +
                    "present: fragment: " + (fragment != null ? fragment.name() : "none") +
                    ", vertex: " + (vertex != null ? vertex.name() : "none") +
                    ", geometry: " + (geometry != null ? geometry.name() : "none") + "\n" +
                    "Log begin __________________________________________________\n" +
                    GL30.glGetProgramInfoLog(programHandle.value()) +
                    "Log end ______________________________________________________";

            throw new DynamicRelinkingError(errMsgToEndAllErrMsgs);
        }

        reloadLocations();
    }

    /**
     * When the program is relinked, the known locations is lost or will be invalid.
     */
    private void reloadLocations() {
        locations.clear(); // Clear existing entries in the map

        // Query the number of active uniforms
        int uniformCount = GL30.glGetProgrami(programHandle.value(), GL30.GL_ACTIVE_UNIFORMS);

        for (int i = 0; i < uniformCount; i++) {
            // Retrieve the uniform name directly
            String uniformName = GL30.glGetActiveUniform(programHandle.value(), i, dummyA, dummyB);

            // Query the location of the uniform
            int location = GL30.glGetUniformLocation(programHandle.value(), uniformName);
            locations.put(uniformName, location);
        }
    }
    //Mem alloc moved to class loader
    private static final IntBuffer dummyA = BufferUtils.createIntBuffer(1), dummyB = BufferUtils.createIntBuffer(1);

    private void handleRemoval(IShader shader, boolean delete){
        if(shader == null) return;

        GL30.glDetachShader(programHandle.value(), shader.getHandle());
        if(delete){
            GL30.glDeleteShader(shader.getHandle());
        }
    }
    @Contract(pure = true)
    private static Error replacementPreFlightCheck(IShader shader){
        if(shader == null) return Error.ON_NULL;

        if(!shader.isCompiled()) {
            return shader.compile();
        }

        return Error.NONE;
    }
    private void recalculatePointer(){
        int res = vertex == null ? 0 : 1;
        res += geometry == null ? 0 : 2;
        res += fragment == null ? 0 : 4;

        renderFuncPointer = switch (res) {
            case 1 -> this::compRenderV;
            case 2 -> this::compRenderG;
            case 3 -> this::renderVG;
            case 4 -> this::compRenderF;
            case 5 -> this::renderVF;
            case 6 -> this::renderGF;
            case 7 -> this::renderVGF;
            default -> this::renderNONE;
        };
    }
    @Override
    public void bind(){
        GL30.glUseProgram(this.programHandle.value());
    }
    @Override
    public void render(Mesh mesh, Matrix4 appliedMatrix){
        bind();
        GL30.glUniformMatrix4fv(locations.get(GLShaderAttr.PROJECTION_MATRIX.glValue()), false, appliedMatrix.val);

        //Bind all mesh data
        bindVertexAttributes(mesh);

        //Bind specific shader stuff
        renderFuncPointer.accept(mesh, appliedMatrix);

        //Execute
        GL30.glDrawElements(GLDrawStyle.TRIANGLES.glValue, mesh.getNumIndices(), GL30.GL_UNSIGNED_SHORT, 0);

        unbind();
        unbindVertexAttributes(mesh);
    }
    @Override
    public void unbind(){
        GL30.glUseProgram(0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }
    private void unbindVertexAttributes(Mesh mesh) {
        for (VertexAttribute attr : mesh.getVertexAttributes()) {
            int location = locations.getOrDefault(attr.alias, -1);
            if (location != -1) {
                GL30.glDisableVertexAttribArray(location);
            }
        }
    }
    private void bindVertexAttributes(Mesh mesh) {
        // Assume your Mesh class has a way to describe its vertex attributes, e.g., a list of attributes
        for (VertexAttribute attr : mesh.getVertexAttributes()) {
            int location = locations.getOrDefault(attr.alias, -1);
            if (location != -1) {
                GL30.glEnableVertexAttribArray(location);
                // Bind the vertex buffer containing the attribute data
                //TODO: Switch to another Mesh (MeshPlus or just raw MeshDataTable). Mesh is too tightly coupled with ShaderProgram to be useful
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, mesh.getVerticesBuffer(false).arrayOffset());
                // Specify the attribute data layout in memory
                GL30.glVertexAttribPointer(
                        location,                  // Attribute location in the shader
                        attr.numComponents,  // Number of components per attribute
                        attr.type,            // Data type of each component
                        false,                     // Normalized
                        attr.getSizeInBytes(),          // Stride
                        attr.offset           // Offset of the first component
                );
            }
        }
    }
    private void compRenderV(Mesh mesh, Matrix4 appliedMatrix){
        vertex.applyBindings();

    }
    private void compRenderG(Mesh mesh, Matrix4 appliedMatrix){
        geometry.applyBindings();

    }
    private void compRenderF(Mesh mesh, Matrix4 appliedMatrix){
        fragment.applyBindings();

    }

    //These could be dynamically generated by combining lambdas, but that would mean allocating more memory during runtime
    private void renderNONE(Mesh mesh, Matrix4 appliedMatrix){}
    private void renderVF(Mesh mesh, Matrix4 appliedMatrix){
        compRenderV(mesh, appliedMatrix);
        compRenderF(mesh, appliedMatrix);
    }
    private void renderGF(Mesh mesh, Matrix4 appliedMatrix){
        compRenderG(mesh, appliedMatrix);
        compRenderF(mesh, appliedMatrix);
    }
    private void renderVG(Mesh mesh, Matrix4 appliedMatrix){
        compRenderV(mesh, appliedMatrix);
        compRenderG(mesh, appliedMatrix);
    }
    private void renderVGF(Mesh mesh, Matrix4 appliedMatrix){
        compRenderV(mesh, appliedMatrix);
        compRenderG(mesh, appliedMatrix);
        compRenderF(mesh, appliedMatrix);
    }

}
