package gbw.melange.core.interactions;

import com.badlogic.gdx.InputProcessor;
import gbw.melange.common.events.OnClick;
import gbw.melange.common.events.interactions.Button;
import gbw.melange.common.events.interactions.Key;
import gbw.melange.events.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GustavBW
 */
@Service
public class InputListener implements IInputListener {

    private final ClickService clickService;

    @Autowired
    public InputListener(ClickService clickService){
        this.clickService = clickService;
    }


    /** {@inheritDoc} */
    @Override
    public boolean keyDown(int i) {
        clickService._keyDown(Key.valueOf(i));
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyUp(int i) {
        clickService._keyUp(Key.valueOf(i));
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        clickService._mouseDown(Button.valueOf(button), x, y, pointer);
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        clickService._mouseUp(Button.valueOf(button), x, y, pointer);
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseMoved(int x, int y) {
        clickService._mouseMoved(x, y);
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
