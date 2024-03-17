package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Any Bi Consumer taking an index and a shader program.
 */
@FunctionalInterface
public interface ShaderResourceBinding {
    /**
     * @param bindIndex managed, unique index to bind a resource to for this shader
     * @param program the shader program itself
     */
    void bind(int bindIndex, ShaderProgram program);
}
