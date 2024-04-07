package gbw.melange.shading.graph.nodes;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public interface IShaderNode {

    /**
     * Get the latest result. If the result is not currently in memory, it'll be read from disk.
     */
    Texture getLatest();

    /**
     * Invalidate the corresponding cache entry. Draw to the internal fbo (if any). Update the cache entry. Update the next node (if any)
     * @param renderBasis what mesh to use for rendering
     */
    void update(Mesh renderBasis);

    /**
     * @param buffer to draw to
     * @param renderBasis what mesh to use for rendering
     * @return whatever buffer the final result is in.
     */
    FrameBuffer drawToFBO(FrameBuffer buffer, Mesh renderBasis);
}
