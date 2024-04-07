package gbw.melange.common.mesh.formatting.providers;

import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;

@FunctionalInterface
public interface SliceProvider<T extends IFloatSlice> extends TriArgProvider<float[], Integer, Integer, T> {
}
