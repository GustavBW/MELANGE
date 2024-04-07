package gbw.melange.shading.components;

import gbw.melange.shading.constants.GLShaderType;

public final class GeometryShader extends ShaderComponent implements IShader{

    public GeometryShader(String localName, String code) {
        super(GLShaderType.GEOMETRY, localName, code);
    }

    @Override
    public void applyBindings() {

    }
    @Override
    public void unbindAny(){

    }
}
