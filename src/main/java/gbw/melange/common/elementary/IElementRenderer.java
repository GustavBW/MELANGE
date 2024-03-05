package gbw.melange.common.elementary;

import java.util.Collection;

public interface IElementRenderer {

    void draw(IElement... elements);
    void draw(Collection<IElement> elements);

}
