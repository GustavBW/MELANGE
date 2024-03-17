package gbw.melange.shading.procedural.noise;

@FunctionalInterface
public interface NoiseProvider {
    /**
     * @return a value in the range 0 to 1
     */
    double getNext();
}
