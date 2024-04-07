package gbw.melange.shading.components;


import gbw.melange.common.shading.components.IGeometryShader;
import gbw.melange.common.shading.constants.GLShaderType;
import gbw.melange.common.shading.constants.ShaderClassification;

public final class GeometryShader extends ShaderComponent implements IGeometryShader {

    public GeometryShader(String localName, String code) {
        super(GLShaderType.GEOMETRY, localName, code);
    }

    @Override
    public ShaderClassification getClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void applyBindings() {

    }
    @Override
    public void unbindAny(){

    }
}
