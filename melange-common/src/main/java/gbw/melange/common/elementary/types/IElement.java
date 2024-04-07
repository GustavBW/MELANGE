package gbw.melange.common.elementary.types;

import com.badlogic.gdx.utils.Disposable;
import gbw.melange.common.elementary.ElementState;
import gbw.melange.common.elementary.styling.IElementStyleDefinition;
import gbw.melange.common.elementary.rules.IElementRuleSet;
import gbw.melange.common.elementary.rules.IElementRuleBuilder;

/**
 * <p>IElement interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IElement<T> extends Disposable, Comparable<IElement<?>>, IConstrainedElement {

    //Standard state getters
    ElementState getState();
    IElementStyleDefinition getStylings();
    int getId();
    //The spice
    IElementRuleBuilder when();
    IElementRuleSet getRuleset();
    T getContent();

}
