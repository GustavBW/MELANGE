package gbw.melange.shading.components;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.shading.constants.GLShaderAttr;
import gbw.melange.shading.errors.DynamicRelinkingError;
import gbw.melange.shading.errors.Error;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DynamicShader {

    protected final Map<String,Integer> locations = new HashMap<>();
    public record ProgramHandle(int value){} //Typealias.
    private final ProgramHandle programHandle;
    private VertexShader vertex = null;
    private FragmentShader fragment = null;
    private GeometryShader geometry = null;
    private BiConsumer<Mesh, Matrix4> renderFuncPointer = this::renderNONE;

    public DynamicShader(){
        this.programHandle = new ProgramHandle(GL30.glCreateProgram());
    }

    /**
     * Set the vertex shader, detach the old one and replace and relink.
     * If the new one hasn't been compiled yet, it will be and any error returned.
     * @param vertex the new vertex shader to couple
     * @param deleteExisting if one was already provided, delete it.
     * @return an error describing what went wrong or {@link Error#NONE}
     */
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


    /**
     * See {@link DynamicShader#setVertex(VertexShader, boolean)}
     */
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

    /**
     * See {@link DynamicShader#setVertex(VertexShader, boolean)}
     */
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
     * Regardless of the amount of shaders present, all available will be rendered in the order:
     * Vertex -> Geometry -> Fragment to the mesh provided and with the matrix applied.
     * @param mesh non null
     * @param appliedMatrix non null
     */
    public void render(Mesh mesh, Matrix4 appliedMatrix){
        GL30.glUseProgram(programHandle.value());
        GL30.glUniformMatrix4fv(locations.get(GLShaderAttr.PROJECTION_MATRIX.glValue()), false, appliedMatrix.val);

        renderFuncPointer.accept(mesh, appliedMatrix);
        unbindAll();
    }

    /**
     *
     * @param toBeAttached
     * @throws DynamicRelinkingError if the linking step fails
     */
    private void attachAndReLink(IShader toBeAttached){
        GL30.glAttachShader(programHandle.value(), toBeAttached.getHandle());
        GL30.glLinkProgram(programHandle.value());

        // Check link status
        if (GL30.glGetProgrami(programHandle.value(), GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
            // Linking failed, retrieve and throw detailed error message
            String errMsgToEndAllErrMsg = "Linking failed for: + " + this + " when : " + toBeAttached + " was introduced. \n" +
                    "present: fragment " + (fragment != null ? fragment.name() : "none") +
                    ", vertex: " + (vertex != null ? vertex.name() : "none") +
                    ", geometry: " + (geometry != null ? geometry.name() : "none") + "\n" +
                    "Log begin __________________________________________________\n" +
                    GL30.glGetProgramInfoLog(programHandle.value()) +
                    "Log end ______________________________________________________";

            throw new DynamicRelinkingError(errMsgToEndAllErrMsg);
        }

        reloadLocations();
    }

    /**
     * When the program is relinked, the known locations is lost or will be invalid.
     */
    private void reloadLocations() {
        locations.clear(); // Clear existing entries in the map
        int programId = programHandle.value();

        // Query the number of active uniforms
        int uniformCount = GL30.glGetProgrami(programId, GL30.GL_ACTIVE_UNIFORMS);

        for (int i = 0; i < uniformCount; i++) {
            // Retrieve the uniform name directly
            String uniformName = GL30.glGetActiveUniform(programId, i, dummyA, dummyB);

            // Query the location of the uniform
            int location = GL30.glGetUniformLocation(programId, uniformName);
            locations.put(uniformName, location);
        }
    }
    private static final IntBuffer dummyA = BufferUtils.createIntBuffer(1), dummyB = BufferUtils.createIntBuffer(1);

    private void handleRemoval(IShader shader, boolean delete){
        if(shader == null) return;

        GL30.glDetachShader(programHandle.value(), shader.getHandle());
        if(delete){
            GL30.glDeleteShader(shader.getHandle());
        }
    }
    private Error replacementPreFlightCheck(IShader shader){
        if(shader == null) return Error.ON_NULL;
        if(!shader.isCompiled()) {
            Error compileErr = shader.compile();
            if(compileErr != Error.NONE){
                return compileErr;
            }
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

    private void unbindAll(){
        GL30.glUseProgram(0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER,0);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    private void compRenderV(Mesh mesh, Matrix4 appliedMatrix){



    }
    private void compRenderG(Mesh mesh, Matrix4 appliedMatrix){

    }
    private void compRenderF(Mesh mesh, Matrix4 appliedMatrix){

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
