package gbw.melange.common.builders;

import gbw.melange.common.elementary.IElement;

public interface IElementBuilder extends IBuilder<IElement> {

    /**
     * Copies all styling attributes of another element.
     */
    IElementBuilder stylingsFrom(IElement element);
    /**
     * Copies all constraint attributes of another element.
     */
    IElementBuilder constraintsFrom(IElement element);


}
