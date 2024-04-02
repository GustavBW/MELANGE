package gbw.melange.mesh.formatting;

import org.jetbrains.annotations.Contract;
/**
 * Wrapper for modifying flat float array buffers, but in a way that is easier to reason about.
 * A window into a specific section of the buffer - like a Slice, but Java happened.
 * Aka: Reference Access Vector
 */
public interface IRefAccVec {

    //Used as method references as an abstract provider
    @Contract(pure = true)
    static RefAccVec2 createVec2(float[] source, int indexFirstComp){
        return new RefAccVec2(source, indexFirstComp);
    }
    @Contract(pure = true)
    static RefAccVec3 createVec3(float[] source, int indexFirstComp){
        return new RefAccVec3(source, indexFirstComp);
    }
    @Contract(pure = true)
    static RefAccVec4 createVec4(float[] source, int indexFirstComp){
        return new RefAccVec4(source, indexFirstComp);
    }

    /**
     * Number of components in this reference access vector
     */
    int entryWidth();

    /**
     * When the series of vector representations was retrieved from the buffer,
     * that this is a part of, what is the index of the first component of this.
     */
    int indexFirstComp();
}
