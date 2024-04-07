package gbw.melange.mesh.constants;

import com.badlogic.gdx.graphics.VertexAttribute;
import gbw.melange.mesh.formatting.providers.SliceProvider;
import gbw.melange.mesh.formatting.providers.TriArgProvider;
import gbw.melange.mesh.formatting.slicing.IFloatSlice;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class VertAttr<T extends IFloatSlice> implements IVertAttr<T> {
    /**
     * compCount, alias and usage - in this order, is the only thing
     * that matters to the meshdatatable.
     * How the data is represented and created is unlimited.
     */
    private final int compCount;
    private final String alias;
    private final int usage;

    private final Class<T> repressentativeClass;
    private final SliceProvider<T> provider;

    @SuppressWarnings("unchecked")
    public VertAttr(int compCount, String alias, int usage) {
        this(compCount, alias, usage, (Class<T>) IFloatSlice.class, (SliceProvider<T>) IFloatSlice.create);
    }

    public VertAttr(int compCount, String alias, int usage, Class<T> clazz, SliceProvider<T> provider){
        this.compCount = compCount;
        this.alias = alias;
        this.usage = usage;
        this.repressentativeClass = clazz;
        this.provider = provider;
    }

    @Override
    public Class<T> repressentativeClass() {
        return repressentativeClass;
    }

    @Override
    public SliceProvider<T> representationProvider() {
        return provider;
    }

    @Override
    public boolean equals(VertexAttribute vAttr) {
        return vAttr.usage == this.usage()
                && vAttr.numComponents == this.compCount()
                && vAttr.alias.equals(this.alias());
    }

    @Override
    public VertexAttribute asVA() {
        return new VertexAttribute(this.usage(), this.compCount(), this.alias());
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IVertAttr<?> asAttr){
            return compareTo(asAttr) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull IVertAttr<? extends IFloatSlice> other) {
        if (other == null) return -1;

        //TODO: If the usage is the same
        if (other.compCount() == this.compCount()
                && Objects.equals(other.alias(), this.alias())
                && other.usage() == this.usage()) {
            return 0;
        }

        return 1;
    }

    @Override
    public int compCount() {
        return compCount;
    }

    @Override
    public String alias() {
        return alias;
    }

    @Override
    public int usage() {
        return usage;
    }


    @Override
    public int hashCode() {
        return Objects.hash(compCount, alias.hashCode(), usage);
    }

    @Override
    public String toString() {
        return "VertAttr[" +
                "compCount=" + compCount + ", " +
                "alias=" + alias + ", " +
                "repClass=" + repressentativeClass + ']';
    }

}
