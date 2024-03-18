package gbw.melange.common.events;

import gbw.melange.common.events.interactions.Button;
import gbw.melange.common.events.interactions.Key;

public interface OnClick {

    @FunctionalInterface
    interface MouseMove{
        void onMove(int onScreenX, int onScreenY);
    }

    void mouse(Button button, Runnable function);
    void mouseDown(Button button, Runnable function);
    void mouseUp(Button button, Runnable function);
    void mouseMove(MouseMove moveAction);
    void key(Key key, Runnable runnable);
    void keyDown(Key key, Runnable runnable);
    void keyUp(Key key, Runnable runnable);


}
