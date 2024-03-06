package gbw.melange.common.builders;

import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.IElement;
import gbw.melange.common.elementary.ElementAnchoring;
import gbw.melange.common.hooks.OnInit;

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

    IElementBuilder<T> onInit(OnInit<T> onInit);



}
