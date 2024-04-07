package gbw.melange.common.shading.components;

import gbw.melange.common.errors.Error;

public interface IShaderComponent {

    /**
     * The assigned handle for this shader. -1 if the shader has yet to be assigned and compiled.
     */
    int getHandle();
    boolean isCompiled();

    /**
     * @return An error containing the shader compilation log on error. Else {@link Error#NONE}
     */
    Error compile(); //GoLang moment

    void applyBindings();
    void unbindAny();

    /**
     * Get the colloquial name of this shader.
     */
    String name();

    /**
     * Get the raw GLSL code of this shader.
     */
    String code();
}
