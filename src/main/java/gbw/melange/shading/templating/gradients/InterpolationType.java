package gbw.melange.shading.templating.gradients;

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
