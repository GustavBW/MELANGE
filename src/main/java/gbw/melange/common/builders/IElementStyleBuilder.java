package gbw.melange.common.builders;

import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.elementary.styling.BevelConfig;
import gbw.melange.shading.IWrappedShader;
import gbw.melange.shading.postprocessing.PostProcessShader;

public interface IElementStyleBuilder<T> extends IPartialBuilder<IElementBuilder<T>> {

    IElementStyleBuilder<T> setBackgroundDrawStyle(GLDrawStyle style);
    IElementStyleBuilder<T> setBorderDrawStyle(GLDrawStyle style);

    /**
     * Set the background color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link gbw.melange.shading.Colors} service through an autowired field in your view. <br/>
     *      <pre>
     *          {@code
     *          @View
     *          public class YourView {
     *             @Autowired
     *             public YourView(Colors colors){
     *
     *             }
     *           }
     *          }
     *      </pre>
     */
    IElementStyleBuilder<T> setBackgroundColor(IWrappedShader shader);

    /**
     * Set the border color, image, gradient or others of this element. <br/>
     * Access various options by grabbing the {@link gbw.melange.shading.Colors} service through an autowired field in your view. <br/>
     *      <pre>
     *          {@code
     *          @View
     *          public class YourView {
     *             @Autowired
     *             public YourView(Colors colors){
     *
     *             }
     *           }
     *          }
     *      </pre>
     */
    IElementStyleBuilder<T> setBorderColor(IWrappedShader shader);
    IElementStyleBuilder<T> setBorderRadius(BevelConfig config);
    IElementStyleBuilder<T> setBorderRadius(double width);
    IElementStyleBuilder<T> setBorderRadius(double width, int subdivs);
    IElementStyleBuilder<T> addPostProcess(PostProcessShader postProcess);
}
