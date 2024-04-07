package gbw.melange.shading.components;

import gbw.melange.shading.constants.GLShaderType;
import gbw.melange.common.errors.Error;
import org.lwjgl.opengl.GL30;

import java.util.Objects;

public abstract class ShaderComponent implements IShader {

    private final GLShaderType type;
    private final String localName;
    private final String code;

    private int assignedHandle = -1;
    private boolean isCompiled = false;
    private Error lastError = null;

    protected ShaderComponent(GLShaderType type, String localName, String code){
        this.type = type;
        this.localName = localName;
        this.code = code;
    }
    @Override
    public int getHandle() {
        return assignedHandle;
    }

    @Override
    public boolean isCompiled() {
        return isCompiled;
    }

    @Override
    public Error compile() {
        if(lastError != null){
            //We know it will always fail if it already has, because the "code" is immutable
            return lastError;
        }

        this.assignedHandle = GL30.glCreateShader(type.glValue);
        GL30.glShaderSource(assignedHandle, code);
        GL30.glCompileShader(assignedHandle);

        if(GL30.glGetShaderi(assignedHandle, GL30.GL_COMPILE_STATUS) == 0){
            lastError = new Error("Compilation Error: " + localName + " \n " + GL30.glGetShaderInfoLog(assignedHandle));
            return lastError;
        }

        isCompiled = true;
        return null;
    }

    public String code() {
        return code;
    }

    public String name(){
        return localName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ShaderComponent) obj;
        return Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Shader{type:"+type+",localName:"+localName+"}";
    }

}
