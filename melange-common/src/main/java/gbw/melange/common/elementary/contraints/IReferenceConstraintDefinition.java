package gbw.melange.common.elementary.contraints;

import gbw.melange.common.elementary.types.IElement;

/**
 * <p>IReferenceConstraintDefinition interface.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public interface IReferenceConstraintDefinition {
    /**
     * <p>attachingAnchor.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    Anchor attachingAnchor();
    /**
     * <p>attachingAnchor.</p>
     *
     * @param value a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    void attachingAnchor(Anchor value);
    /**
     * <p>contentAnchor.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    Anchor contentAnchor();
    /**
     * <p>contentAnchor.</p>
     *
     * @param value a {@link gbw.melange.common.elementary.contraints.Anchor} object
     */
    void contentAnchor(Anchor value);
    /**
     * <p>sizingPolicy.</p>
     *
     * @return a {@link gbw.melange.common.elementary.contraints.SizingPolicy} object
     */
    SizingPolicy sizingPolicy();
    /**
     * <p>sizingPolicy.</p>
     *
     * @param policy a {@link gbw.melange.common.elementary.contraints.SizingPolicy} object
     */
    void sizingPolicy(SizingPolicy policy);
    /**
     * <p>borderWidth.</p>
     *
     * @return a double
     */
    double borderWidth();
    /**
     * <p>borderWidth.</p>
     *
     * @param value a double
     */
    void borderWidth(double value);
    /**
     * <p>padding.</p>
     *
     * @return a double
     */
    double padding();
    /**
     * <p>padding.</p>
     *
     * @param value a double
     */
    void padding(double value);
    /**
     * <p>elementAttachedTo.</p>
     *
     * @return a {@link gbw.melange.common.elementary.types.IElement} object
     */
    IElement<?> elementAttachedTo();
    /**
     * <p>elementAttachedTo.</p>
     *
     * @param value a {@link gbw.melange.common.elementary.types.IElement} object
     */
    void elementAttachedTo(IElement<?> value);
}
