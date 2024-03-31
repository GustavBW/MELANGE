package gbw.melange.shading.errors;

public record Error(String msg) {
    public static final Error ON_NULL = new Error("Null not allowed"),
    NONE = new Error("No error!");


}
