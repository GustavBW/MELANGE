package gbw.melange.shading.components;

import gbw.melange.shading.errors.Error;

public interface IShader {

    /**
     * The assigned handle for this shader. -1 if the shader has yet to be assigned and compiled.
     */
    int getHandle();
    boolean isCompiled();

    /**
     * @return An error containing the shader compilation log on error. Else {@link Error#NONE}
     */
    Error compile(); //GoLang moment

}
