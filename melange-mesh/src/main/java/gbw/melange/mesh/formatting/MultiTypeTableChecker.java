package gbw.melange.mesh.formatting;

import gbw.melange.common.errors.Error;
import gbw.melange.mesh.constants.IVertAttr;
import gbw.melange.mesh.formatting.slicing.IFloatSlice;
import gbw.melange.mesh.formatting.slicing.ISliceVec2;
import gbw.melange.mesh.formatting.slicing.ISliceVec3;
import gbw.melange.mesh.formatting.slicing.ISliceVec4;

public class MultiTypeTableChecker {

    public static <T extends IFloatSlice> Error checkKey(IVertAttr<T> key){
        if(key.compCount() <= 0){
            return new Error("A vertex attribute component count of " + key.compCount() + " is not supported.");
        }
        if(key.repressentativeClass() == null){
            return new Error("A vertex attribute should contain what class best represents any data stored under this attribute.");
        }
        if(key.representationProvider() == null){
            return new Error("A vertex attribute should declare how to produce the class that represents any data stored under this attribute.");
        }
        Error specificKnownCases = switch(key.compCount()) {
            case 2 -> {
                if(key.repressentativeClass().isAssignableFrom(ISliceVec2.class)){
                    yield Error.NONE;
                }
                yield new Error("For a 2 component vertex attribute, the representative class should implement ISliceVec2.");
            }
            case 3 -> {
                if(key.repressentativeClass().isAssignableFrom(ISliceVec3.class)){
                    yield Error.NONE;
                }
                yield new Error("For a 3 component vertex attribute, the representative class should implement ISliceVec3.");
            }
            case 4 -> {
                if(key.repressentativeClass().isAssignableFrom(ISliceVec4.class)){
                    yield Error.NONE;
                }
                yield new Error("For a 4 component vertex attribute, the representative class should implement ISliceVec4.");
            }
            default -> Error.NONE;
        };
        if(key.compCount() > 4 && !key.repressentativeClass().isAssignableFrom(IFloatSlice.class)){
            return new Error("For representing the data of attributes of any component count, use something that implements IFloatSlice.");
        }
        return specificKnownCases;
    }
}
