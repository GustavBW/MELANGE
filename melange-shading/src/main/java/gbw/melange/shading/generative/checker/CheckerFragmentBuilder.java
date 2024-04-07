package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.Color;
import gbw.melange.common.shading.generative.checker.CheckerShaderAttr;
import gbw.melange.common.shading.generative.checker.ICheckerBuilder;
import gbw.melange.common.shading.generative.checker.ICheckerShader;
import gbw.melange.shading.GLSL;
import gbw.melange.common.shading.constants.ShaderClassification;
import gbw.melange.shading.components.IFragmentShader;
import gbw.melange.shading.components.VertexShader;
import gbw.melange.common.shading.services.IShaderPipeline;

public class CheckerFragmentBuilder implements ICheckerBuilder {

    private Color colorA = new Color(0,0,0, 1);
    private Color colorB = new Color(1,1,1, 1);
    private float rows = 2;
    private float columns = 2;
    private final IShaderPipeline pipeline;
    private final String localName;
    public CheckerFragmentBuilder(String localName, IShaderPipeline pipeline){
        this.pipeline = pipeline;
        this.localName = localName;
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

        IFragmentShader fragment = new IFragmentShader(localName, sb, ShaderClassification.COMPLEX, true);
        ICheckerShader shader = new CheckerShader(localName, VertexShader.DEFAULT, fragment, true);
        if(pipeline != null){
            pipeline.registerForCompilation(shader);
        }
        shader.setColumns(columns);
        shader.setRows(rows);
        return shader;
    }


    private String generateOpeningStatement(){
        return """
        #ifdef GL_ES
            precision mediump float;
        #endif
        
        varying vec2 v_texCoords;
        """ +
        "uniform float " + CheckerShaderAttr.ROWS + ";\n" +
        "uniform float " + CheckerShaderAttr.COLUMNS + ";\n" +
        "const vec4 colorA = " + GLSL.toString(colorA) + ";\n" +
        "const vec4 colorB = " + GLSL.toString(colorB) + ";\n";
    }

    private String generateMainDrawingCode() {
        return "\nvoid main() { " +
            "\tvec2 coord = v_texCoords;\n" +
            "\tcoord.x *= " + CheckerShaderAttr.COLUMNS + ";\n" +
            "\tcoord.y *= " + CheckerShaderAttr.ROWS + ";\n" +
            """
            // Determine whether we're in an even or odd row and column
            int col = int(floor(coord.x));
            int row = int(floor(coord.y));
            bool isOddRow = mod(float(row), 2.0) > 0.5;
            bool isOddCol = mod(float(col), 2.0) > 0.5;

            // Determine cell color based on row and column parity
            vec4 color = (isOddRow && isOddCol) || (!isOddRow && !isOddCol) ? colorA : colorB;
            
            // Handle partial tiles at edges if necessary
            if(coord.x > float(col) + 1.0 || coord.y > float(row) + 1.0) {
                color = vec4(0.0); // or some other logic for edge handling
            }

            gl_FragColor = color;
        }
        """;
    }

}
