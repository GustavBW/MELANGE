package gbw.melange.common.elementary.styling;

import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

import java.util.List;

/**
 * <p>IReferenceStyleDefinition interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IReferenceStyleDefinition {
    /**
     * <p>backgroundShader.</p>
     *
     * @return a {@link gbw.melange.shading.IWrappedShader} object
     */
    IWrappedShader backgroundShader();
    /**
     * <p>backgroundShader.</p>
     *
     * @param program a {@link gbw.melange.shading.IWrappedShader} object
     */
    void backgroundShader(IWrappedShader program);
    /**
     * <p>borderShader.</p>
     *
     * @return a {@link gbw.melange.shading.IWrappedShader} object
     */
    IWrappedShader borderShader();
    /**
     * <p>borderShader.</p>
     *
     * @param program a {@link gbw.melange.shading.IWrappedShader} object
     */
    void borderShader(IWrappedShader program);
    /**
     * <p>backgroundDrawStyle.</p>
     *
     * @return a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    GLDrawStyle backgroundDrawStyle();
    /**
     * <p>backgroundDrawStyle.</p>
     *
     * @param style a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    void backgroundDrawStyle(GLDrawStyle style);
    /**
     * <p>borderDrawStyle.</p>
     *
     * @return a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    GLDrawStyle borderDrawStyle();
    /**
     * <p>borderDrawStyle.</p>
     *
     * @param style a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    void borderDrawStyle(GLDrawStyle style);
    /**
     * <p>borderBevel.</p>
     *
     * @return a {@link gbw.melange.common.elementary.styling.BevelConfig} object
     */
    BevelConfig borderBevel();
    /**
     * <p>borderBevel.</p>
     *
     * @param op a {@link gbw.melange.common.elementary.styling.BevelConfig} object
     */
    void borderBevel(BevelConfig op);
    /**
     * <p>postProcesses.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<PostProcessShader> postProcesses();


}
