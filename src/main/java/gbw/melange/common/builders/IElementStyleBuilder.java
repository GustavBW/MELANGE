package gbw.melange.common.builders;

import gbw.melange.common.gl.GLDrawStyle;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.shading.services.Colors;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.IPostProcessShader;

/**
 * <p>IElementStyleBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementStyleBuilder<T> extends IPartialBuilder<IElementBuilder<T>> {

    /**
     * <p>setBackgroundDrawStyle.</p>
     *
     * @param style a {@link gbw.melange.common.gl.GLDrawStyle} object
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style);
    /**
     * <p>setBorderDrawStyle.</p>
     *
     * @param style a {@link gbw.melange.common.gl.GLDrawStyle} object
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style);

    /**
     * Set the background color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link Colors} service through an autowired field in your view. <br/>
     */
    IElementStyleBuilder<T> setBackgroundColor(IWrappedShader shader);

    /**
     * Set the border color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link Colors} service through an autowired field in your view. <br/>
     */
    IElementStyleBuilder<T> setBorderColor(IWrappedShader shader);
    /**
     * <p>setBorderRadius.</p>
     *
     * @param config a {@link gbw.melange.common.elementary.styling.BevelConfig} object
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> setBorderRadius(BevelConfig config);
    /**
     * <p>setBorderRadius.</p>
     *
     * @param width a double
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> setBorderRadius(double width);
    /**
     * <p>setBorderRadius.</p>
     *
     * @param width a double
     * @param subdivs a int
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> setBorderRadius(double width, int subdivs);
    /**
     * <p>addPostProcess.</p>
     *
     * @param postProcess a {@link IPostProcessShader} object
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> addPostProcess(IPostProcessShader postProcess);
}
