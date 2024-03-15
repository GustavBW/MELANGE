package gbw.melange.common.gl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

public class Util {

    public static void clearError(){
        while (Gdx.gl.glGetError() != GL30.GL_NO_ERROR) {
        }
    }
}
