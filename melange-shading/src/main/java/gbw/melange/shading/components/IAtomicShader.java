package gbw.melange.shading.components;

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
    Error setVertex(VertexShader vertex, boolean deleteExisting);

    /**
     * See {@link AtomicShader#setVertex(VertexShader, boolean)}
     */
    Error setFragment(FragmentShader fragment, boolean deleteExisting);

    /**
     * See {@link AtomicShader#setVertex(VertexShader, boolean)}
     */
    Error setGeometry(GeometryShader geometry, boolean deleteExisting);

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
     * You will absolutely mess up OpenGL if you change the components of this shader using {@link IAtomicShader#setFragment(FragmentShader, boolean)},
     * {@link IAtomicShader#setVertex(VertexShader, boolean)} or {@link IAtomicShader#setGeometry(GeometryShader, boolean)} while bound.
     */
    void bind();
    /**
     * Unbind all resources that might have been bound by manual use of this shader.
     */
    void unbind();
}
