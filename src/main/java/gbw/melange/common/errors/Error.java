package gbw.melange.common.errors;

public record Error(String msg) {
    public static final gbw.melange.shading.errors.Error
            ON_NULL = new gbw.melange.shading.errors.Error("Null not allowed"),
            NONE = new gbw.melange.shading.errors.Error("No error!");
}
