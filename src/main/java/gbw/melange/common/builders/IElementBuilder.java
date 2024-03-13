package gbw.melange.common.builders;

import com.badlogic.gdx.graphics.Mesh;
import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;

/**
 * @param <T> return type of OnInit if any
 */
public interface IElementBuilder<T> extends IBuilder<IElement<T>> {

    /**
     * Copies all styling attributes of another element.
     */
    IElementBuilder<T> stylingsFrom(IElement<?> element);
    IElementBuilder<T> stylingsFrom(IReferenceStyleDefinition refStyDef);
    /**
     * Copies all constraint attributes of another element.
     */
    IElementBuilder<T> constraintsFrom(IElement<?> element);
    IElementBuilder<T> constraintsFrom(IReferenceConstraintDefinition refConDef);


    IElementBuilder<T> setMesh(Mesh mesh);
    IElementBuilder<T> contentProvider(IContentProvider<T> onInit);

    IElementStyleBuilder<T> styling();
    IElementConstraintBuilder<T> constraints();


}
