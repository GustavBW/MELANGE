package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import gbw.melange.mesh.formatting.providers.SliceProvider;
import gbw.melange.mesh.formatting.slicing.IFloatSlice;

/**
 * A VertexAttribute, however also the dedicated keytype for the {@link gbw.melange.mesh.formatting.IMeshDataTable} multitype table.
 * @param <T>
 */
public interface IVertAttr<T extends IFloatSlice> extends Comparable<IVertAttr<? extends IFloatSlice>> {
    boolean equals(VertexAttribute vAttr);

    VertexAttribute asVA();

    int compCount();

    String alias();

    int usage();

    /**
     * What type can best represent each entry of some data under this attribute.
     */
    Class<T> repressentativeClass();
    SliceProvider<T> representationProvider();
}
