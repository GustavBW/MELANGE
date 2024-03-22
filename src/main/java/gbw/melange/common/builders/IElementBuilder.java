package gbw.melange.common.builders;

import gbw.melange.common.elementary.*;
import gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition;
import gbw.melange.common.elementary.styling.IReferenceStyleDefinition;
import gbw.melange.common.elementary.types.IElement;
import gbw.melange.mesh.IManagedMesh;

/**
 * <p>IElementBuilder interface.</p>
 *
 * @param <T> return type of OnInit if any
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElementBuilder<T> extends IBuilder<IElement<T>> {

    /**
     * Copies all styling attributes of another element.
     *
     * @param element a {@link gbw.melange.common.elementary.types.IElement} object
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<T> stylingsFrom(IElement<?> element);
    /**
     * <p>stylingsFrom.</p>
     *
     * @param refStyDef a {@link gbw.melange.common.elementary.styling.IReferenceStyleDefinition} object
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<T> stylingsFrom(IReferenceStyleDefinition refStyDef);
    /**
     * Copies all constraint attributes of another element.
     *
     * @param element a {@link gbw.melange.common.elementary.types.IElement} object
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<T> constraintsFrom(IElement<?> element);
    /**
     * <p>constraintsFrom.</p>
     *
     * @param refConDef a {@link gbw.melange.common.elementary.contraints.IReferenceConstraintDefinition} object
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<T> constraintsFrom(IReferenceConstraintDefinition refConDef);

    /**
     * <p>setMesh.</p>
     *
     * @param mesh a {@link com.badlogic.gdx.graphics.Mesh} object
     * @return a {@link gbw.melange.common.builders.IElementBuilder} object
     */
    IElementBuilder<T> setShape(IManagedMesh mesh);
    IElementBuilder<T> setContent(IContentProvider<T> provider);
    IElementBuilder<T> setContent(T content);
    /**
     * <p>styling.</p>
     *
     * @return a {@link gbw.melange.common.builders.IElementStyleBuilder} object
     */
    IElementStyleBuilder<T> styling();
    /**
     * <p>constraints.</p>
     *
     * @return a {@link gbw.melange.common.builders.IElementConstraintBuilder} object
     */
    IElementConstraintBuilder<T> constraints();


}
