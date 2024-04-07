package gbw.melange.common.errors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import org.apache.logging.log4j.Logger;

public final class Errors {

    /**
     * Throws an exception immediately if a gl error is present.
     * Also prepends the error code to any message provided:
     * <pre>
     * {@code "| GL Error <errVal> | + <your message>"}
     * </pre>
     */
    public static void checkAndThrow(String msg){ //Delete body on release build
        int someGlErr = Gdx.gl.glGetError();
        if(someGlErr != GL30.GL_NO_ERROR){
            throw new RuntimeException("| GL Error " + someGlErr + " | " + msg);
        }
    }

    /**
     * Invokes the logger and prints the message, prepended error code much like {@link gbw.melange.shading.errors.Errors#checkAndThrow(String)},
     * if a gl error is present
     */
    public static void checkAndLog(Logger logger, String msg){ //Delete body on release build
        int someGlErr = Gdx.gl.glGetError();
        if(someGlErr != GL30.GL_NO_ERROR){
            logger.warn("| GL Error " + someGlErr + " | " + msg);
        }
    }

    @FunctionalInterface
    public interface ThrowingRunn<R extends Throwable>{
        void apply() throws R;
    }
    @FunctionalInterface
    public interface ErrorProvider{
        Error run();
    }

    /**
     * Escalate anything to a runtime exception.
     * @param func void func no args but throws
     * @param <R> type of exception
     */
    public static <R extends Throwable> void escalate(ThrowingRunn<R> func){
        try{
            func.apply();
        }catch (Throwable r){
            throw new RuntimeException(r);
        }
    }

    public static void escalate(ErrorProvider func){
        Error err = func.run();
        if(err != Error.NONE){
            throw new RuntimeException(err.msg());
        }
    }

    /**
     * Deescalate anything that throws and return the message as a String instead
     * @param func void func no args but throws
     */
    public static <R extends Throwable> Error deescalate(ThrowingRunn<R> func){
        try{
            func.apply();
        }catch (Throwable r){
            return new Error(r.getMessage());
        }
        return Error.NONE;
    }

}

