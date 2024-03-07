package gbw.melange.common.builders;

import gbw.melange.common.elementary.Anchor;
import gbw.melange.common.elementary.ElementAnchoring;

public interface IElementConstraintBuilder<T> extends IPartialBuilder<IElementBuilder<T>>{
    IElementConstraintBuilder<T> setSelfAnchor(ElementAnchoring anchor);
    IElementConstraintBuilder<T> setSelfAnchor(Anchor anchor);
    IElementConstraintBuilder<T> setAttachingAnchor(ElementAnchoring anchor);
    IElementConstraintBuilder<T> setAttachingAnchor(Anchor anchor);
    IElementConstraintBuilder<T> setPadding(double percent);
    IElementConstraintBuilder<T> setBorderWidth(double percent);
}
