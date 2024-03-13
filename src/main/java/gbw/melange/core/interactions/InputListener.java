package gbw.melange.core.interactions;

import com.badlogic.gdx.InputProcessor;
import org.springframework.stereotype.Service;

/**
 * <p>InputListener class.</p>
 *
 * @author GustavBW
 * @version $Id: $Id
 */
@Service
public class InputListener implements IInputListener {
    /** {@inheritDoc} */
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyUp(int i) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
