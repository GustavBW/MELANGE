package gbw.melange.common.shading.components;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;
import gbw.melange.common.errors.Error;

/**
 * An AtomicShader is not the most performant shader abstraction ever, but highly flexible.
 * Consider using specializations for specific purposes
 */
public interface IAtomicShader {
    /**
     * Set the vertex shader, detach the old one and replace and relink.
     * If the new one hasn't been compiled yet, it will be and any error returned.
     *
     * @param vertex         the new vertex shader to couple
     * @param deleteExisting if one was already provided, delete it.
     * @return an error describing what went wrong or {@link Error#NONE}
     */
    Error setVertex(IVertexShader vertex, boolean deleteExisting);

    /**
     * See {@link IAtomicShader#setVertex(IVertexShader, boolean)}
     */
    Error setFragment(IFragmentShader fragment, boolean deleteExisting);

    /**
     * See {@link IAtomicShader#setVertex(IVertexShader, boolean)}
     */
    Error setGeometry(IGeometryShader geometry, boolean deleteExisting);

    /**
     * Regardless of the amount of shaders present, all available will be rendered in the order:
     * Vertex -> Geometry -> Fragment to the mesh provided and with the matrix applied.
     *
     * @param mesh          non null
     * @param appliedMatrix non null
     */
    void render(Mesh mesh, Matrix4 appliedMatrix);

    /**
     * Bind all resources currently available to this shader for manual use.
     * You will absolutely mess up OpenGL if you change the components of this shader using {@link IAtomicShader#setFragment(IFragmentShader, boolean)},
     * {@link IAtomicShader#setVertex(IVertexShader, boolean)} or {@link IAtomicShader#setGeometry(IGeometryShader, boolean)} while bound.
     */
    void bind();
    /**
     * Unbind all resources that might have been bound by manual use of this shader.
     */
    void unbind();
}
