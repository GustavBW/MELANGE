package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.shading.GLSLUtil;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import gbw.melange.shading.services.IShaderPipeline;

import java.util.ArrayList;

public class CheckerFragmentBuilder implements ICheckerBuilder {

    private Color colorA = new Color(0,0,0, 1);
    private Color colorB = new Color(1,1,1, 1);
    private float rotation = 0;
    private float rows = 2;
    private float columns = 2;
    private final IShaderPipeline pipeline;
    private final String localName;
    public CheckerFragmentBuilder(String localName, IShaderPipeline pipeline){
        this.pipeline = pipeline;
        this.localName = localName;
    }

    @Override
    public ICheckerBuilder setRotation(double degrees) {
        this.rotation = (float) degrees;
        return this;
    }

    @Override
    public ICheckerBuilder setFirstColor(Color color) {
        this.colorA = color;
        return this;
    }

    @Override
    public ICheckerBuilder setSecondColor(Color color) {
        this.colorB = color;
        return this;
    }

    @Override
    public ICheckerBuilder setRows(double count) {
        this.rows = (float) count;
        return this;
    }

    @Override
    public ICheckerBuilder setColumns(double count) {
        this.columns = (float) count;
        return this;
    }

    @Override
    public ICheckerShader build() {
        String sb = generateOpeningStatement() +
                generateMainDrawingCode();

        FragmentShader fragment = new FragmentShader(localName, sb, ShaderClassification.COMPLEX, true);
        ICheckerShader shader = new CheckerShader(localName, VertexShader.DEFAULT, fragment, true, new ArrayList<>());
        if(pipeline != null){
            pipeline.registerForCompilation(shader);
        }
        shader.setColumns(columns);
        shader.setRows(rows);
        shader.setRotation(rotation);
        return shader;
    }


    private String generateOpeningStatement(){
        return """
        #ifdef GL_ES
            precision mediump float;
        #endif
        
        varying vec2 v_texCoords;
        """ +
        "uniform float " + CheckerShaderAttr.ROTATION + ";\n" +
        "uniform float " + CheckerShaderAttr.ROWS + ";\n" +
        "uniform float " + CheckerShaderAttr.COLUMNS + ";\n" +
        "const colorA = " + GLSLUtil.toString(colorA) + ";\n" +
        "const colorB = " + GLSLUtil.toString(colorB) + ";\n";
    }

    private String generateMainDrawingCode() {
        return "\nvoid main() { " +
            "\tmat2 rotationMatrix = mat2(cos("+CheckerShaderAttr.ROTATION+"), -sin("+CheckerShaderAttr.ROTATION+"), sin("+CheckerShaderAttr.ROTATION+"), cos("+CheckerShaderAttr.ROTATION+"));" + """
            vec2 center = vec2(0.5, 0.5);
            vec2 adjustedCoords = (rotationMatrix * (v_texCoords - center)) + center;

            """ +
            "\tadjustedCoords.x *= " + CheckerShaderAttr.COLUMNS + ";\n" +
            "\tadjustedCoords.y *= " + CheckerShaderAttr.ROWS + ";\n" +
            """
            // Determine whether we're in an even or odd row and column
            int col = int(floor(adjustedCoords.x));
            int row = int(floor(adjustedCoords.y));
            bool isOddRow = mod(float(row), 2.0) > 0.5;
            bool isOddCol = mod(float(col), 2.0) > 0.5;

            // Determine cell color based on row and column parity
            vec4 color = (isOddRow && isOddCol) || (!isOddRow && !isOddCol) ? colorA : colorB;
            
            // Handle partial tiles at edges if necessary
            if(adjustedCoords.x > float(col) + 1.0 || adjustedCoords.y > float(row) + 1.0) {
                color = vec4(0.0); // or some other logic for edge handling
            }

            gl_FragColor = color;
        }
        """;
    }

}
