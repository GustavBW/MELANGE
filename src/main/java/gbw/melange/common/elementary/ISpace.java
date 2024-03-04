package gbw.melange.common.elementary;

import gbw.melange.common.builders.IElementBuilder;

public interface ISpace {

    /**
     * Create a new element belonging to this ScreenSpaceBoundSpace.
     */
    IElementBuilder createElement();

}
