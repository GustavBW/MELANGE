package gbw.melange.mesh.formatting;

import java.util.Objects;

public final class RefAccVec4 implements IRefAccVec, IRefAccVec4 {
    private final float[] source;
    private int indexFirstComp;

    public RefAccVec4(float[] source, int indexFirstComp) {
        this.source = source;
        this.indexFirstComp = indexFirstComp;
    }

    @Override
    public float x() {
        return source[indexFirstComp];
    }
    @Override
    public float y() {
        return source[indexFirstComp + 1];
    }
    @Override
    public float z() {
        return source[indexFirstComp + 2];
    }
    @Override
    public float w() {
        return source[indexFirstComp + 3];
    }
    @Override
    public void x(float value) {
        source[indexFirstComp] = value;
    }
    @Override
    public void y(float value) {
        source[indexFirstComp + 1] = value;
    }
    @Override
    public void z(float value) {
        source[indexFirstComp + 2] = value;
    }
    @Override
    public void w(float value) {
        source[indexFirstComp + 3] = value;
    }

    @Override
    public int entryWidth() {
        return 3;
    }

    @Override
    public int indexFirstComp() {
        return indexFirstComp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RefAccVec4) obj;
        return Objects.equals(this.source, that.source) &&
                this.indexFirstComp == that.indexFirstComp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, indexFirstComp);
    }

    @Override
    public String toString() {
        return "RefAccVec4[" +
                "source=" + source + ", " +
                "indexFirstComp=" + indexFirstComp + ']';
    }

    /* package private, must be accessed only by the IMeshDataTable that issued this instance */
    void setIndexOfFirstComponent(int newIndex){
        this.indexFirstComp = newIndex;
    }
}
