package gbw.melange.common.builders;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.elementary.*;
import gbw.melange.common.gl_wrappers.GLDrawStyle;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.shading.FragmentShader;

/**
 * @param <T> return type of OnInit if any
 */
public interface IElementBuilder<T> extends IBuilder<IElement> {

    /**
     * Copies all styling attributes of another element.
     */
    IElementBuilder<T> stylingsFrom(IElement element);
    IElementBuilder<T> stylingsFrom(IReferenceStyleDefinition refStyDef);
    /**
     * Copies all constraint attributes of another element.
     */
    IElementBuilder<T> constraintsFrom(IElement element);
    IElementBuilder<T> constraintsFrom(IReferenceConstraintDefinition refConDef);


    IElementBuilder<T> attachTo(IElement element);
    IElementBuilder<T> setMesh(Mesh mesh);
    IElementBuilder<T> onInit(OnInit<T> onInit);

    IElementStyleBuilder<T> styling();
    IElementConstraintBuilder<T> constraints();


}
