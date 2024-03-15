package gbw.melange.common.elementary.styling;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

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
     * @return a {@link gbw.melange.shading.IWrappedShader} object
     */
    IWrappedShader getBackgroundShader();
    /**
     * <p>getBorderShader.</p>
     *
     * @return a {@link gbw.melange.shading.IWrappedShader} object
     */
    IWrappedShader getBorderShader();

    /**
     * <p>getBackgroundDrawStyle.</p>
     *
     * @return a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    GLDrawStyle getBackgroundDrawStyle(); //TODO: Swap to Enum
    /**
     * <p>getBorderDrawStyle.</p>
     *
     * @return a {@link gbw.melange.common.gl.GLDrawStyle} object
     */
    GLDrawStyle getBorderDrawStyle();//TODO: Swap to Enum

    /**
     * <p>getBorderBevel.</p>
     *
     * @return a {@link gbw.melange.common.elementary.styling.BevelConfig} object
     */
    BevelConfig getBorderBevel();
    /**
     * <p>getPostProcesses.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<PostProcessShader> getPostProcesses();

}
