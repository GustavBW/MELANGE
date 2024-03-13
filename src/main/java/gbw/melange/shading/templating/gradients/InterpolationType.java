package gbw.melange.shading.templating.gradients;

/**
 * <p>InterpolationType class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
public enum InterpolationType {
    /**
     * Linear progression between value a and b: A straight line.
     */
    LINEAR,
    /**
     * Looks like a logistic distribution (S-curve).
     */
    HERMIT,
    QUADRATIC;
}
