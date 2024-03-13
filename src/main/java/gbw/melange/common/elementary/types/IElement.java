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
    /**
     * <p>getState.</p>
     *
     * @return a {@link gbw.melange.common.elementary.ElementState} object
     */
    ElementState getState();
    /**
     * <p>getStylings.</p>
     *
     * @return a {@link gbw.melange.common.elementary.styling.IElementStyleDefinition} object
     */
    IElementStyleDefinition getStylings();
    /**
     * <p>getId.</p>
     *
     * @return a int
     */
    int getId();
    //The spice
    /**
     * <p>when.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementRuleBuilder} object
     */
    IElementRuleBuilder when();
    /**
     * <p>getRuleset.</p>
     *
     * @return a {@link gbw.melange.common.elementary.rules.IElementRuleSet} object
     */
    IElementRuleSet getRuleset();

    /**
     * <p>getContent.</p>
     *
     * @return a T object
     */
    T getContent();

}
