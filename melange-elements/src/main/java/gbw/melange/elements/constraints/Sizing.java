package gbw.melange.elements.constraints;

/**
 * Only applicable to the Void. (EmptySpace elements)
 * @param x percent of available space in the x direction. If infinite, default to screenspace.
 * @param y percent of available space in the y direction. If infinite, default to screenspace.
 */
public record Sizing(double x, double y) {
}
