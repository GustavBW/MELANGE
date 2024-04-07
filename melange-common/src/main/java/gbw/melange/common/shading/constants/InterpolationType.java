package gbw.melange.common.shading.constants;

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
    /**
     * float out = 1.0 - ( 1.0 / (in + 1.0));
     */
    LOGARITHMIC,
    QUADRATIC,
    NONE;
}
