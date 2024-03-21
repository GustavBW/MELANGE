package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.shading.constants.ShaderClassification;
import gbw.melange.shading.generative.partial.FragmentShader;
import gbw.melange.shading.generative.partial.VertexShader;

import java.util.ArrayList;

public class BoxBlurShader extends PostProcessShader implements IBoxBlurShader {
    public BoxBlurShader(String localName) {
        super(
                localName,
                VertexShader.DEFAULT,
                new FragmentShader("FRAG_" + localName, GAUSSIAN_BLUR, ShaderClassification.COMPLEX, false),
                false,
                new ArrayList<>()
        );
    }

    private int kernelSize = 9;

    @Override
    public void setKernelSize(int value) {
        this.kernelSize = value;
    }

    @Override
    public void applyChildBindings(ShaderProgram program){
        program.setUniformi(BlurShaderAttr.KERNEL_SIZE.glValue, kernelSize);

        super.applyChildBindings(program);
    }

    public static final String GAUSSIAN_BLUR = """
    #ifdef GL_ES
    precision mediump float;
    #endif
    
    varying vec2 v_texCoords;
    uniform sampler2D u_texture;
    uniform int kernelSize; // This is actually the kernel radius
    uniform float kernel[81]; // Maximum size based on max kernel radius, here 4, leading to a 9x9 kernel
    
    void main() {
        vec2 offset = 1.0 / textureSize(u_texture, 0);
        vec4 sum = vec4(0.0);
        int size = 2 * kernelSize + 1; // Actual kernel size
        for (int i = -kernelSize; i <= kernelSize; i++) {
            for (int j = -kernelSize; j <= kernelSize; j++) {
                // Correct indexing for a variable-sized kernel centered around (kernelSize, kernelSize)
                int index = (i + kernelSize) + (j + kernelSize) * size;
                sum += texture2D(u_texture, v_texCoords + vec2(i, j) * offset) * kernel[index];
            }
        }
        gl_FragColor = sum;
    }
    """;

}
