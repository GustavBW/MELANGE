package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoxBlurShader extends PostProcessShader implements IBoxBlurShader {
    private static final Logger log = LogManager.getLogger();
    public BoxBlurShader(String localName, int kernelSize) {
        super(
                localName,
                VertexShader.DEFAULT,
                new FragmentShader("FRAG_" + localName, BOX_BLUR, ShaderClassification.COMPLEX, false),
                false
        );
        this.kernelSize = kernelSize;
    }

    private int kernelSize = 9;
    private boolean hasChanged = true;

    @Override
    public void setKernelSize(int value) {
        if(value > 9){
            //This is due to the dynamic nature and that the default kernel weight array only has 81 entires.
            //If expanded, the limit could be pushed too.
            log.warn("The BoxBlur shader does not support kernelsizes of greater that 9 currently");
            return;
        }

        hasChanged = value != kernelSize;
        this.kernelSize = value;
    }

    @Override
    public void applyChildBindings(ShaderProgram program){
        program.setUniformi(BlurShaderAttr.KERNEL_SIZE.glValue, kernelSize);
        program.setUniformf("u_textureSize", super.getTexture().getWidth(), super.getTexture().getHeight());

        super.applyChildBindings(program);
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


    public static final String BOX_BLUR = """
    #ifdef GL_ES
    precision mediump float;
    #endif
        
    varying vec2 v_texCoords;
    uniform sampler2D u_texture;
    uniform int kernelSize; // This is actually the kernel radius
    uniform vec2 u_textureSize; // Assuming you're passing the texture size
    
    void main() {
        vec2 texelSize = 1.0 / u_textureSize;
        vec4 sum = vec4(0.0);
        int size = 2 * kernelSize + 1; // Actual kernel size
        float weight = 1.0 / float(size * size); // Compute weight based on kernel size
    
        for (int i = -kernelSize; i <= kernelSize; i++) {
            for (int j = -kernelSize; j <= kernelSize; j++) {
                vec2 offset = vec2(float(i), float(j)) * texelSize;
                sum += texture2D(u_texture, v_texCoords + offset) * weight;
            }
        }
    
        gl_FragColor = sum;
    }
    """;

}
