package gbw.melange.common.builders;

import gbw.melange.common.elementary.contraints.Anchor;
import gbw.melange.common.elementary.contraints.ElementAnchoring;
import gbw.melange.common.elementary.types.IElement;

public interface IElementConstraintBuilder<T> extends IPartialBuilder<IElementBuilder<T>>{
    IElementConstraintBuilder<T> setContentAnchor(ElementAnchoring anchor);
    IElementConstraintBuilder<T> setContentAnchor(Anchor anchor);
    IElementConstraintBuilder<T> setAttachingAnchor(ElementAnchoring anchor);
    IElementConstraintBuilder<T> setAttachingAnchor(Anchor anchor);
    IElementConstraintBuilder<T> setPadding(double percent);
    IElementConstraintBuilder<T> setBorderWidth(double percent);
    IElementConstraintBuilder<T> attachTo(IElement<?> element);
}
