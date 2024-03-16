package gbw.melange.shading.postprocessing;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Type alias for ShaderProgram for now
 */
public record PostProcessShader(ShaderProgram program) {
    public static final String GAUSSIAN_BLUR = """
            #ifdef GL_ES
                precision mediump float;
            #endif
            
            varying vec2 v_texCoords;
            uniform sampler2D u_texture;
            uniform int kernelSize;
            uniform float kernel[9];
            
            main () {
                vec2 offset = 1.0 / textureSize(u_texture, 0);
                vec4 sum = vec4(0.0);
                for (int i = -kernelSize; i <= kernelSize; i++) {
                    for (int j = -kernelSize; j <= kernelSize; j++) {
                        sum += texture2D(u_texture, v_texCoords + vec2(i, j) * offset) * kernel[i + kernelSize * (j + kernelSize)];
                    }
                }
                gl_FragColor = sum;
            }
            """;
}
