package gbw.melange.common.elementary.styling;

import gbw.melange.shading.constants.GLDrawStyle;
import gbw.melange.mesh.modifiers.BevelConfig;
import gbw.melange.shading.IManagedShader;
import gbw.melange.shading.postprocessing.IPostProcessShader;

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
     * @return a {@link IManagedShader} object
     */
    IManagedShader<?> backgroundShader();
    /**
     * <p>backgroundShader.</p>
     *
     * @param program a {@link IManagedShader} object
     */
    void backgroundShader(IManagedShader<?> program);
    /**
     * <p>borderShader.</p>
     *
     * @return a {@link IManagedShader} object
     */
    IManagedShader<?> borderShader();
    /**
     * <p>borderShader.</p>
     *
     * @param program a {@link IManagedShader} object
     */
    void borderShader(IManagedShader<?> program);
    /**
     * <p>backgroundDrawStyle.</p>
     *
     * @return a {@link GLDrawStyle} object
     */
    GLDrawStyle backgroundDrawStyle();
    /**
     * <p>backgroundDrawStyle.</p>
     *
     * @param style a {@link GLDrawStyle} object
     */
    void backgroundDrawStyle(GLDrawStyle style);
    /**
     * <p>borderDrawStyle.</p>
     *
     * @return a {@link GLDrawStyle} object
     */
    GLDrawStyle borderDrawStyle();
    /**
     * <p>borderDrawStyle.</p>
     *
     * @param style a {@link GLDrawStyle} object
     */
    void borderDrawStyle(GLDrawStyle style);
    /**
     * <p>borderBevel.</p>
     *
     * @return a {@link BevelConfig} object
     */
    BevelConfig borderBevel();
    /**
     * <p>borderBevel.</p>
     *
     * @param op a {@link BevelConfig} object
     */
    void borderBevel(BevelConfig op);
    /**
     * <p>postProcesses.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<IPostProcessShader> postProcesses();


}
