package gbw.melange.shading;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

@FunctionalInterface
public interface ShaderResourceBinding {
    /**
     * @param bindIndex managed, unique index to bind a resource to for this shader
     * @param program the shader program itself
     */
    void bind(int bindIndex, ShaderProgram program);
}
