package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.shading.generative.checker.CheckerShaderAttr;
import gbw.melange.common.shading.generative.checker.ICheckerShader;
import gbw.melange.common.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.GenerativeShader;
import gbw.melange.common.shading.components.IFragmentShader;
import gbw.melange.common.shading.components.IVertexShader;

public class CheckerShader extends GenerativeShader<ICheckerShader> implements ICheckerShader {
    public CheckerShader(String localName, IVertexShader vertex, IFragmentShader fragment, boolean isStatic) {
        super(localName, vertex, fragment, isStatic);
    }

    private double rows = 2;
    private double columns = 2;
    private boolean hasChanged = true;

    @Override
    public void setColumns(double count) {
        hasChanged = count != columns;
        this.columns = count;
    }

    @Override
    public void setRows(double count) {
        hasChanged = count != rows;
        this.rows = count;
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniformf(CheckerShaderAttr.COLUMNS.glValue, (float) columns);
        program.setUniformf(CheckerShaderAttr.ROWS.glValue, (float) rows);
        hasChanged = false;
    }

    @Override
    protected boolean hasChildPropertiesChanged() {
        return hasChanged;
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected ICheckerShader copyChild() {
        return new CheckerShader(super.getLocalName(), getVertex(), getFragment(), isStatic());
    }

    @Override
    protected ICheckerShader copyChildAs(String newLocalName) {
        return new CheckerShader(newLocalName, getVertex(), getFragment(), isStatic());
    }

    @Override
    protected void disposeChildSpecificResources() {}


}
