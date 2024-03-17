package gbw.melange.shading.errors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import org.apache.logging.log4j.Logger;

public class Errors {

    /**
     * Throws an exception immediately if a gl error is present.
     * Also prepends the error code to any message provided:
     * <pre>
     * {@code "| GL Error <errVal> | + <your message>"}
     * </pre>
     */
    public static void checkAndThrow(String msg){
        int someGlErr = Gdx.gl.glGetError();
        if(someGlErr != GL30.GL_NO_ERROR){
            throw new RuntimeException("| GL Error " + someGlErr + " | " + msg);
        }
    }

    /**
     * Invokes the logger and prints the message, prepended error code much like {@link Errors#checkAndThrow(String)},
     * if a gl error is present
     */
    public static void checkAndLog(Logger logger, String msg){
        int someGlErr = Gdx.gl.glGetError();
        if(someGlErr != GL30.GL_NO_ERROR){
            logger.warn("| GL Error " + someGlErr + " | " + msg);
        }
    }

    public static void debugLogAllGLInfo(Logger logger){

    }

}
