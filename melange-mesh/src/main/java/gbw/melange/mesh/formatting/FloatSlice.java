package gbw.melange.mesh.formatting;

import gbw.melange.common.mesh.formatting.slicing.IFloatSlice;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author GustavBW
 */
public class FloatSlice implements IFloatSlice {
    protected float[] source; //Officially final. But cache invalidation
    private int indexFirstComp; //Officially final. But cache invalidation
    private final int width;
    public FloatSlice(float[] source, int indexFirstComp, int width) {
        this.source = source;
        this.indexFirstComp = indexFirstComp;
        this.width = width;
    }

    @Override
    public int entryWidth() {
        return width;
    }

    @Override
    public int indexFirstComp() {
        return indexFirstComp;
    }

    @Override
    public float get(int index){
        if(isIllegalAccess(index)){
            throw new ArrayIndexOutOfBoundsException("Tried to get index " + index + " of " + this + " with a length of " + entryWidth());
        }
        return source[indexFirstComp() + index];
    }
    @Override
    public void set(int index, float value){
        if(isIllegalAccess(index)){
            throw new ArrayIndexOutOfBoundsException("Tried to set index " + index + " of " + this + " with a length of " + entryWidth());
        }
        source[indexFirstComp() + index] = value;
    }
    private boolean isIllegalAccess(int index){
        return index < 0 || index >= entryWidth();
    }

    @Override
    public void forEachFloat(FloatConsumer func) {
        for(int i = 0; i < width; i++){
            func.accept(source[indexFirstComp + i]);
        }
    }

    @Override
    public FloatSlice copy() {
        return new FloatSlice(source, indexFirstComp, width);
    }

    @Override
    public void forEach(Consumer<? super Float> action) {
        //Yeah this is iterable doing its thing.
        //I hope the documentation in the interface is enough for ppl to use the right one.
        for(int i = 0; i < width; i++){
            action.accept(source[indexFirstComp + i]);
        }
    }

    private class FloatIterator implements Iterator<Float> {
        private int currentIndex = indexFirstComp();

        @Override
        public boolean hasNext() {
            return currentIndex < indexFirstComp() + width && currentIndex < source.length;
        }

        @Override
        public Float next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Automatically boxes the float to Float
            return source[currentIndex++];
        }

        // Optional: A non-boxing next method
        public float nextFloat() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return source[currentIndex++];
        }

        public void reset(){
            this.currentIndex = indexFirstComp;
        }
    }

    private final FloatIterator theIterator = new FloatIterator();
    @NotNull
    @Override
    public Iterator<Float> iterator() {
        theIterator.reset();
        return theIterator;
    }

    @Override
    public Spliterator<Float> spliterator() {
        throw new UnsupportedOperationException("Splitterating an IFloatSlice is not supported.");
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FloatSlice) obj;
        return Arrays.equals(this.source, that.source) &&
                this.indexFirstComp() == that.indexFirstComp()
                && this.entryWidth() == that.entryWidth();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(source), indexFirstComp(), entryWidth());
    }

    @Override
    public String toString() {
        return "FloatSlice[" +
                "source: " + Arrays.hashCode(source) + ", " +
                "indexFirstComp: " + indexFirstComp() + ", " +
                "length: " + entryWidth() + ']';
    }

    /* package private, must be accessed only by the IMeshDataTable that issued this instance */
    void setIndexOfFirstComponent(int newIndex){
        this.indexFirstComp = newIndex;
    }
    /* package private, must be accessed only by the IMeshDataTable that issued this instance */
    void setSource(float[] newSource){
        this.source = newSource;
    }
}
