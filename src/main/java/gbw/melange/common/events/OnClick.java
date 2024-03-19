package gbw.melange.common.events;

import gbw.melange.common.events.interactions.Button;
import gbw.melange.common.events.interactions.Key;

public interface OnClick {

    @FunctionalInterface
    interface MouseHandler {
        boolean handle(int onScreenX, int onScreenY);
    }

    @FunctionalInterface
    interface eventHandler{
        boolean on();
    }

    void mouse(Button button, MouseHandler function);
    void mouseDown(Button button, MouseHandler function);
    void mouseUp(Button button, MouseHandler function);
    void mouseMove(MouseHandler function);
    void key(Key key, Runnable runnable);
    void keyDown(Key key, Runnable runnable);
    void keyUp(Key key, Runnable runnable);

    void whileKeyHeld(Key key, Runnable runnable);
    void whileButtonHeld(Button button, Runnable runnable);


}
