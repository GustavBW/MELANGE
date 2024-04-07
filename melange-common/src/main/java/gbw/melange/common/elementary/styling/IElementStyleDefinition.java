package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.shading.constants.GLDrawStyle;
import gbw.melange.common.mesh.modifiers.IBevelConfig;
import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.postprocess.IPostProcessShader;

import java.util.List;

/**
 * <p>IElementStyleDefinition interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementStyleDefinition extends Disposable {

    /**
     * <p>getBackgroundShader.</p>
     *
     * @return a {@link IManagedShader} object
     */
    IManagedShader<?> getBackgroundShader();
    /**
     * <p>getBorderShader.</p>
     *
     * @return a {@link IManagedShader} object
     */
    IManagedShader<?> getBorderShader();

    /**
     * <p>getBackgroundDrawStyle.</p>
     *
     * @return a {@link GLDrawStyle} object
     */
    GLDrawStyle getBackgroundDrawStyle();
    /**
     * <p>getBorderDrawStyle.</p>
     *
     * @return a {@link GLDrawStyle} object
     */
    GLDrawStyle getBorderDrawStyle();

    /**
     * <p>getBorderBevel.</p>
     *
     * @return a {@link IBevelConfig} object
     */
    IBevelConfig getBorderBevel();
    /**
     * <p>getPostProcesses.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<IPostProcessShader> getPostProcesses();

}
