package gbw.melange.mesh.formatting.providers;

import gbw.melange.mesh.formatting.slicing.IFloatSlice;

@FunctionalInterface
public interface SliceProvider<T extends IFloatSlice> extends TriArgProvider<float[], Integer, Integer, T> {
}
