package gbw.melange.common.builders;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.hooks.OnInit;
import gbw.melange.shading.FragmentShader;

//TODO: Split into ElementBuilder, ElementConstraintBuilder, & ElementStyleBuilder
public interface IElementBuilder<T> extends IBuilder<IElement> {

    /**
     * Copies all styling attributes of another element.
     */
    IElementBuilder<T> stylingsFrom(IElement element);
    /**
     * Copies all constraint attributes of another element.
     */
    IElementBuilder<T> constraintsFrom(IElement element);
    IElementBuilder<T> setSelfAnchor(ElementAnchoring anchor);
    IElementBuilder<T> setSelfAnchor(Anchor anchor);
    IElementBuilder<T> setAttachingAnchor(ElementAnchoring anchor);
    IElementBuilder<T> setAttachingAnchor(Anchor anchor);
    IElementBuilder<T> attachTo(IElement element);
    IElementBuilder<T> setBackgroundColor(FragmentShader fragment);
    IElementBuilder<T> setBorderColor(FragmentShader fragment);
    IElementBuilder<T> setBackgroundColor(ShaderProgram shader);
    IElementBuilder<T> setBorderColor(ShaderProgram shader);
    IElementBuilder<T> setPadding(double percent);
    IElementBuilder<T> setBorderWidth(double percent);
    IElementBuilder<T> setMesh(Mesh mesh);
    IElementBuilder<T> setBackgroundDrawStyle(int style); //TODO: Swap to enum
    IElementBuilder<T> setBorderDrawStyle(int style); //TODO: Swap to Enum
    IElementBuilder<T> onInit(OnInit<T> onInit);



}
