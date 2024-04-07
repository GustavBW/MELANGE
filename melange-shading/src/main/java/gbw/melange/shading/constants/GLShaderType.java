package gbw.melange.shading.constants;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public enum GLShaderType {

    UNKNOWN(-1),
    FRAGMENT(GL30.GL_FRAGMENT_SHADER),
    VERTEX(GL30.GL_VERTEX_SHADER),
    GEOMETRY(GL32.GL_GEOMETRY_SHADER);
    //Tesselation is GL40 stuff

    public final int glValue;

    GLShaderType(int glValue){
        this.glValue = glValue;
    }

}
