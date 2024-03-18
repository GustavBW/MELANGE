package gbw.melange.events;

import gbw.melange.common.events.OnClick;
import gbw.melange.common.events.interactions.Button;
import gbw.melange.common.events.interactions.Key;
import org.springframework.stereotype.Service;

@Service
public class ClickService implements OnClick {
    @Override
    public void mouse(Button button, Runnable function) {

    }

    @Override
    public void mouseDown(Button button, Runnable function) {

    }

    @Override
    public void mouseUp(Button button, Runnable function) {

    }

    @Override
    public void mouseMove(MouseMove moveAction) {

    }

    @Override
    public void key(Key key, Runnable runnable) {

    }

    @Override
    public void keyDown(Key key, Runnable runnable) {

    }

    @Override
    public void keyUp(Key key, Runnable runnable) {

    }
}
