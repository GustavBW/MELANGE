package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import gbw.melange.shading.ManagedShader;
import gbw.melange.shading.ShaderResourceBinding;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.gradients.GradientShader;
import gbw.melange.shading.generative.gradients.GradientShaderAttr;
import gbw.melange.shading.generative.gradients.IGradientShader;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.List;

public class CheckerShader extends ManagedShader<ICheckerShader> implements ICheckerShader {
    public CheckerShader(String localName, VertexShader vertex, FragmentShader fragment, boolean isStatic, List<ShaderResourceBinding> bindings) {
        super(localName, vertex, fragment, isStatic, bindings);
    }

    private double rows = 2;
    private double columns = 2;

    @Override
    public void setColumns(double count) {
        this.columns = count;
    }

    @Override
    public void setRows(double count) {
        this.rows = count;
    }

    @Override
    protected void applyChildBindings(ShaderProgram program) {
        program.setUniformf(CheckerShaderAttr.COLUMNS.glValue, (float) columns);
        program.setUniformf(CheckerShaderAttr.ROWS.glValue, (float) rows);
    }

    @Override
    protected ShaderClassification getChildClassification() {
        return ShaderClassification.COMPLEX;
    }

    @Override
    protected ICheckerShader copyChild() {
        return new CheckerShader(super.getLocalName(), getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected ICheckerShader copyChildAs(String newLocalName) {
        return new CheckerShader(newLocalName, getVertex(), getFragment(), isStatic(), bindings);
    }

    @Override
    protected void disposeChildSpecificResources() {}


}
