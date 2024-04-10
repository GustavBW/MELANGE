package gbw.melange.model.typealias;

import gbw.melange.model.errors.MalformedGLObjectIssue;

/**
 * Typealias-ish for an opengl object handle.
 * Includes a small error check if the handle is invalid and will throw a {@link MalformedGLObjectIssue} if it is.
 * @param handle int
 */
public record GLObjectHandle(int handle){

    public GLObjectHandle {
        if (handle < 0) {
            throw new MalformedGLObjectIssue("A gl object handle should not be less than 0.");
        }
    }

}
