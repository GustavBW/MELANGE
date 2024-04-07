package gbw.melange.common.builders;


import gbw.melange.common.shading.IManagedShader;
import gbw.melange.common.shading.constants.GLDrawStyle;
import gbw.melange.common.shading.postprocess.IPostProcessShader;
import gbw.melange.common.shading.services.Colors;

/**
 * <p>IElementStyleBuilder interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementStyleBuilder<T> extends IPartialBuilder<IElementBuilder<T>> {

    IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style);

    /**
     * Set the background color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link Colors} service through an autowired field in your view. <br/>
     */
    IElementStyleBuilder<T> setBackgroundColor(IManagedShader<?> shader);

    /**
     * Set the border color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link Colors} service through an autowired field in your view. <br/>
     */
    IElementStyleBuilder<T> setBorderColor(IManagedShader<?> shader);
    IElementStyleBuilder<T> setBorderRadius(BevelConfig config);
    IElementStyleBuilder<T> setBorderRadius(double width);
    IElementStyleBuilder<T> setBorderRadius(double width, int subdivs);
    IElementStyleBuilder<T> addPostProcess(IPostProcessShader postProcess);
}
