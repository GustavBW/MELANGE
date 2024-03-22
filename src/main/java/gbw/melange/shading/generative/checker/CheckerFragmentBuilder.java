package gbw.melange.shading.generative.checker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class CheckerFragmentBuilder implements ICheckerBuilder {

    private Color colourA = new Color(0,0,0, 1);
    private Color colourB = new Color(1,1,1, 1);
    private float rotation = 0;
    private float rows = 2;
    private float columns = 2;

    @Override
    public ICheckerBuilder setRotation(double degrees) {
        this.rotation = (float) degrees;
        return this;
    }

    @Override
    public ICheckerBuilder setFirstColour(Color color) {
        this.colourA = color;
        return this;
    }

    @Override
    public ICheckerBuilder setSecondColour(Color color) {
        this.colourB = color;
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
        StringBuilder sb = new StringBuilder();
        sb.append(generateOpeningStatement());



        return null;
    }


    private static String generateOpeningStatement(){
        return """
        #ifdef GL_ES
            precision mediump float;
        #endif
        
        varying vec2 v_texCoords;  
        """ +
        "uniform float " + CheckerShaderAttr.ROTATION.glValue + ";\n" +
        "uniform float " + CheckerShaderAttr.ROWS.glValue + ";\n" +
        "uniform float " + CheckerShaderAttr.COLUMNS.glValue + ";\n";
    }


}
